package controller

import model.GameMode
import model.SudokuBoard

class SudokuController {

    val board = SudokuBoard()

    var mode: GameMode = GameMode.CREATION
        private set

    fun switchMode(newMode: GameMode) {
        mode = newMode
    }

}
