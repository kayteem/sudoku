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

    // Wert direkt setzen
    fun setCellValue(row: Int, col: Int, value: Int?) {
        val cell = board.getCell(row, col)
        if (mode == GameMode.CREATION) {
            cell.value = value
            cell.candidates.clear()
        }
    }

    // Kandidat toggeln
    fun toggleCandidate(row: Int, col: Int, digit: Int) {
        if (mode == GameMode.RIDDLE) {
            board.getCell(row, col).toggleCandidate(digit)
        }
    }
}
