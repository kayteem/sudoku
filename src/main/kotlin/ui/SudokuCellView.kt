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

    private val FONT_GIVEN_VALUE: Font = Font.font("System", FontWeight.EXTRA_BOLD, 24.0)
    private val FONT_USER_VALUE: Font = Font.font("System", FontWeight.NORMAL, 24.0)
    private val FONT_CANDIDATE: Font = Font.font("System", FontWeight.NORMAL, 14.0)

    private val cell: SudokuCell
    private val valueText: Text
    private val candidateGrid: GridPane

    init {
        initStackPane()

        cell = controller.board.getCell(row, col)

        valueText = Text()
        valueText.font = Font.font(24.0)

        candidateGrid = GridPane()
        candidateGrid.hgap = 6.0
        candidateGrid.vgap = 3.0
        candidateGrid.alignment = Pos.CENTER
        candidateGrid.prefWidth = 50.0
        candidateGrid.prefHeight = 50.0

        children.addAll(candidateGrid, valueText)

        setOnMouseClicked { event ->
            handleClick(event.button)
        }

        updateView()
    }

    private fun handleClick(button: MouseButton) {
        when (controller.mode) {
            GameMode.CREATION -> {
                if (cell.value != null) {
                    cell.value = null
                }
            }

            GameMode.RIDDLE -> {
                if (cell.isMutable) return  // feste Zellen nicht verändern
                if (cell.value != null) {
                    if (button == MouseButton.SECONDARY) {
                        // Wert zurücksetzen, vorherige Kandidaten bleiben erhalten
                        // TODO: val oldCandidates = cell.activeCandidates.toSet()
                        cell.value = null
                        // TODO: cell.activeCandidates.clear()
                        // TODO: cell.activeCandidates.addAll(oldCandidates)
                    }
                } else {
                    // TODO: if (!riddleGridCreated) createRiddleGrid(cell)
                }
            }
        }

        updateView()
    }

    private fun updateView() {

        // value defined? show value and hide candidates!
        if (cell.value != null) {
            valueText.text = cell.value.toString()
            valueText.font = if (cell.isMutable) FONT_USER_VALUE else FONT_GIVEN_VALUE
            valueText.isVisible = true

            candidateGrid.children.clear()
            candidateGrid.isVisible = false
        }

        // value undefined - hide value and show candidates!
        else {
            valueText.text = ""
            valueText.isVisible = false

            populateCandidatesGrid()
            candidateGrid.isVisible = true
        }
    }

    private fun populateCandidatesGrid() {
        candidateGrid.children.clear()

        for (i in 1..9) {
            createCandidateCell(i)
        }
    }

    private fun createCandidateCell(i: Int) {
        val label = Text(i.toString())
        label.font = FONT_CANDIDATE
        label.fill = if (i in cell.candidates) Color.BLACK else Color.DARKGRAY

        val r = (i - 1) / 3
        val c = (i - 1) % 3
        candidateGrid.add(label, c, r)

        label.setOnMouseClicked { mouseEvent ->
            when (controller.mode) {
                GameMode.CREATION -> {
                    cell.value = i
                    cell.isMutable = false
                    cell.clearCandidates()
                }
                GameMode.RIDDLE -> {
                    if (!cell.isMutable)
                        return@setOnMouseClicked

                    when (mouseEvent.button) {
                        MouseButton.PRIMARY -> {

                        }
                        MouseButton.SECONDARY -> {

                        }
                        else -> {}
                    }
                    cell.toggleCandidate(i)
                }
            }

            updateView()
        }
    }


    // helpers
    private fun initStackPane() {

        // init pane
        prefWidth = 75.0
        prefHeight = 75.0
        alignment = Pos.CENTER
        isFocusTraversable = false

        // set cell borders
        val topWidth = if (row == 0) 3 else 1
        val leftWidth = if (col == 0) 3 else 1
        val bottomWidth = if ((row + 1) % 3 == 0) 3 else 1
        val rightWidth = if ((col + 1) % 3 == 0) 3 else 1

        style = "-fx-border-color: black black black black;" +
                "-fx-border-width: $topWidth $rightWidth $bottomWidth $leftWidth;" +
                "-fx-background-color: white;"
    }

}
