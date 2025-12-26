package ui

import controller.SudokuController
import javafx.geometry.Pos
import javafx.scene.control.Button
import javafx.scene.control.Label
import javafx.scene.layout.BorderPane
import javafx.scene.layout.GridPane
import javafx.scene.layout.HBox
import model.GameMode

class SudokuGridView : BorderPane() {

    private val PADDING = 10.0
    private val GAP = 2.0

    private val controller: SudokuController = SudokuController()
    private val grid: GridPane = GridPane()
    private val headerBox: HBox = HBox(PADDING)

    init {
        headerBox.alignment = Pos.CENTER
        headerBox.padding = javafx.geometry.Insets(PADDING)

        top = headerBox
        center = createGrid()

        updateHeader()
    }

    private fun updateHeader() {
        headerBox.children.clear()

        val titleLabel = Label()
        val buttons = mutableListOf<Button>()

        when (controller.mode) {
            GameMode.CREATION -> {
                titleLabel.text = "Erstellungs-Modus"

                buttons.add(Button("Rätsel starten").apply {
                    setOnAction {
                        controller.switchMode(GameMode.RIDDLE)
                        updateHeader()
                    }
                })
            }

            GameMode.RIDDLE -> {
                titleLabel.text = "Rätsel-Modus"

                buttons.add(Button("Rätsel beenden").apply {
                    setOnAction {
                        controller.switchMode(GameMode.CREATION)
                        updateHeader()
                    }
                })
            }
        }

        headerBox.children.add(titleLabel)
        headerBox.children.addAll(buttons)
    }

    private fun createGrid(): GridPane {
        grid.alignment = Pos.CENTER
        grid.hgap = GAP
        grid.vgap = GAP

        for (row in 0 until 9) {
            for (col in 0 until 9) {
                val cellView = SudokuCellView(controller, row, col)
                grid.add(cellView, col, row)
            }
        }
        return grid
    }
}
