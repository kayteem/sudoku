package model

class SudokuBoard {

    private val cells: Array<Array<SudokuCell>> =
        Array(9) { Array(9) { SudokuCell() } }

    fun getCell(row: Int, col: Int): SudokuCell =
        cells[row][col]

    fun clearBoard() {
        for (row in 0 until 9) {
            for (col in 0 until 9) {
                val cell = cells[row][col]
                cell.value = null
                cell.candidates.clear()
                cell.activeCandidates.clear()
            }
        }
    }
}
