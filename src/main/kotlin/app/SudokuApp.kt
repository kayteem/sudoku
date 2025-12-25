package app

import javafx.application.Application
import javafx.scene.Scene
import javafx.stage.Stage
import ui.SudokuGridView

class SudokuApp : Application() {

    override fun start(stage: Stage) {
        val root = SudokuGridView()
        stage.scene = Scene(root, 600.0, 650.0)
        stage.title = "Sudoku"
        stage.show()
    }
}

fun main() {
    Application.launch(SudokuApp::class.java)
}
