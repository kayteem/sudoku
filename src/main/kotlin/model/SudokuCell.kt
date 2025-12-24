package model

class SudokuCell {

    var value: Int? = null
    var isMutable: Boolean = true
    val candidates = mutableSetOf<Int>()

    fun clearCandidates() {
        candidates.clear()
    }

    fun toggleCandidate(digit: Int) {
        if (digit !in 1..9)
            return

        if (digit in candidates) {
            candidates.remove(digit)
        } else {
            candidates.add(digit)
        }
    }

}