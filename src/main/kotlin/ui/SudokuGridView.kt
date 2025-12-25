package ui

import controller.SudokuController
import javafx.geometry.Pos
import javafx.scene.control.ToggleButton
import javafx.scene.layout.BorderPane
import javafx.scene.layout.GridPane
import model.GameMode

class SudokuGridView : BorderPane() {

    private val controller = SudokuController()
    private val grid = GridPane()

    init {
        top = createModeToggle()
        center = createGrid()
    }

    private fun createModeToggle(): ToggleButton {
        val toggle = ToggleButton("Erstellungs-Modus")

        toggle.setOnAction {
            if (toggle.isSelected) {
                toggle.text = "Rätsel-Modus"
                controller.switchMode(GameMode.RIDDLE)
            } else {
                toggle.text = "Erstellungs-Modus"
                controller.switchMode(GameMode.CREATION)
            }
        }
        return toggle
    }

    private fun createGrid(): GridPane {
        grid.alignment = Pos.CENTER
        grid.hgap = 2.0
        grid.vgap = 2.0

        for (row in 0 until 9) {
            for (col in 0 until 9) {
                val cellView = SudokuCellView(controller, row, col)
                grid.add(cellView, col, row)
            }
        }
        return grid
    }
}
