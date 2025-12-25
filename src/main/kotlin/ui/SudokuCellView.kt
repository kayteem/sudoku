package ui

import controller.SudokuController
import javafx.geometry.Pos
import javafx.scene.input.MouseButton
import javafx.scene.layout.StackPane
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

    private val cell: SudokuCell
    private val valueText: Text
    private val candidatesGridView: CandidatesGridView

    init {
        initStackPane()

        cell = controller.board.getCell(row, col)

        valueText = Text()
        valueText.font = Font.font(24.0)

        candidatesGridView = CandidatesGridView(
            controller = controller,
            cell = cell,
            updateParentCallback = ::updateView
        )

        children.addAll(candidatesGridView, valueText)

        setOnMouseReleased { event ->
            handleCellClick(event.button)
        }

        updateView()
    }

    private fun handleCellClick(button: MouseButton) {
        println("clicked cell ${row + 1}:${col + 1}")

        if (button != MouseButton.SECONDARY)
            return

        if (controller.mode == GameMode.RIDDLE && !cell.isMutable)
            return

        cell.value = null
        updateView()
    }

    private fun updateView() {
        println("updating cell ${row + 1}:${col + 1}")

        // value defined? show value and hide candidates!
        if (cell.value != null) {
            valueText.text = cell.value.toString()
            valueText.font = if (cell.isMutable) FONT_USER_VALUE else FONT_GIVEN_VALUE
            valueText.isVisible = true

            candidatesGridView.hide()
        }

        // value undefined - hide value and show candidates!
        else {
            valueText.text = ""
            valueText.isVisible = false

            candidatesGridView.populate()
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
