package ui

import controller.SudokuController
import javafx.geometry.Pos
import javafx.scene.input.MouseButton
import javafx.scene.layout.GridPane
import javafx.scene.layout.StackPane
import javafx.scene.paint.Color
import javafx.scene.text.Font
import javafx.scene.text.FontWeight
import javafx.scene.text.Text
import model.GameMode
import model.SudokuCell


class SudokuCellView(
    private val controller: SudokuController,
    private val row: Int,
    private val col: Int
) : StackPane() {

    private val FONT_GIVEN_VALUE = Font.font("System", FontWeight.EXTRA_BOLD, 24.0)
    private val FONT_USER_VALUE = Font.font("System", FontWeight.NORMAL, 24.0)
    private val FONT_CANDIDATE = Font.font("System", FontWeight.NORMAL, 14.0)

    private val valueText = Text()
    private val candidateGrid = GridPane()

    private var creationGridVisible = false
    private var riddleGridCreated = false

    init {
        prefWidth = 75.0
        prefHeight = 75.0
        alignment = Pos.CENTER

        updateCellBorders()

        valueText.font = Font.font(24.0)
        children.addAll(candidateGrid, valueText)

        candidateGrid.hgap = 6.0
        candidateGrid.vgap = 3.0
        candidateGrid.alignment = Pos.CENTER
        candidateGrid.prefWidth = 50.0
        candidateGrid.prefHeight = 50.0

        isFocusTraversable = false

        setOnMouseClicked { event ->
            handleClick(event.button)
        }

        updateView()
    }

    private fun updateCellBorders() {
        val topWidth = if (row == 0) 3 else 1
        val leftWidth = if (col == 0) 3 else 1
        val bottomWidth = if ((row + 1) % 3 == 0) 3 else 1
        val rightWidth = if ((col + 1) % 3 == 0) 3 else 1

        style = "-fx-border-color: black black black black;" +
                "-fx-border-width: $topWidth $rightWidth $bottomWidth $leftWidth;" +
                "-fx-background-color: white;"
    }

    private fun handleClick(button: MouseButton) {
        val cell = controller.board.getCell(row, col)

        when (controller.mode) {

            GameMode.ERSTELLUNG -> {
                when (button) {
                    MouseButton.PRIMARY -> {
                        if (cell.value == null && !creationGridVisible) {
                            updateCreationGrid(cell)
                            creationGridVisible = true
                        }
                    }
                    MouseButton.SECONDARY -> {
                        cell.value = null
                        cell.isGiven = false
                        candidateGrid.children.clear()
                        creationGridVisible = false
                    }
                    else -> {}
                }
            }

            GameMode.RAETSEL -> {
                if (cell.isGiven) return  // feste Zellen nicht verändern
                if (cell.value != null) {
                    if (button == MouseButton.SECONDARY) {
                        // Wert zurücksetzen, vorherige Kandidaten bleiben erhalten
                        val oldCandidates = cell.activeCandidates.toSet()
                        cell.value = null
                        cell.activeCandidates.clear()
                        cell.activeCandidates.addAll(oldCandidates)
                    }
                } else {
                    if (!riddleGridCreated) createRiddleGrid(cell)
                }
            }
        }

        updateView()
    }

    private fun updateCreationGrid(cell: SudokuCell) {
        candidateGrid.children.clear()

        for (i in 1..9) {
            val t = Text(i.toString())
            t.font = FONT_CANDIDATE
            t.fill = Color.BLACK
            val r = (i - 1) / 3
            val c = (i - 1) % 3
            candidateGrid.add(t, c, r)

            t.setOnMouseClicked { _ ->
                cell.value = i
                cell.candidates.clear()
                candidateGrid.children.clear()
                creationGridVisible = false
                updateView()
            }
        }
        candidateGrid.isVisible = true
    }

    private fun createRiddleGrid(cell: SudokuCell) {
        candidateGrid.children.clear()
        for (i in 1..9) {
            val t = Text(i.toString())
            t.font = FONT_CANDIDATE
            t.fill = if (i in cell.activeCandidates) Color.BLACK else Color.DARKGRAY
            val r = (i - 1) / 3
            val c = (i - 1) % 3
            candidateGrid.add(t, c, r)

            t.setOnMouseClicked { event ->
                if (cell.isGiven) return@setOnMouseClicked
                when (event.button) {
                    MouseButton.PRIMARY -> {
                        if (i in cell.activeCandidates) {
                            cell.value = i
                        } else {
                            cell.activeCandidates.add(i)
                            cell.candidates.add(i)
                        }
                    }
                    MouseButton.SECONDARY -> {
                        cell.activeCandidates.remove(i)
                        cell.candidates.remove(i)
                    }
                    else -> {}
                }
                updateView()
            }
        }
        candidateGrid.isVisible = true
        riddleGridCreated = true
    }

    fun updateView() {
        val cell = controller.board.getCell(row, col)

        // value defined
        if (cell.value != null) {
            valueText.text = cell.value.toString()
            valueText.isVisible = true
            valueText.font = if (cell.isGiven) FONT_GIVEN_VALUE else FONT_USER_VALUE

            candidateGrid.isVisible = false
        }

        // value undefined
        else {
            valueText.text = ""
            valueText.isVisible = false

            // TODO: harmonize!
            candidateGrid.isVisible = when (controller.mode) {
                GameMode.ERSTELLUNG -> creationGridVisible
                GameMode.RAETSEL -> true
            }

            // TODO: ALWAYS UPDATE GRID!
            if (controller.mode == GameMode.RAETSEL && candidateGrid.children.isEmpty()) {
                createRiddleGrid(cell)
            }

            candidateGrid.children.forEach { node ->
                if (node is Text) {
                    val digit = node.text.toInt()
                    node.fill = if (digit in cell.activeCandidates) Color.BLACK else Color.DARKGRAY
                }
            }
        }
    }

}
