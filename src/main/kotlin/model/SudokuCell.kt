package model

class SudokuCell {

    var value: Int? = null
    val candidates = mutableSetOf<Int>()
    val activeCandidates = mutableSetOf<Int>()
    var isGiven: Boolean = false

    fun clearValue() {
        value = null
    }

    fun clearCandidates() {
        candidates.clear()
        activeCandidates.clear()
    }

    fun toggleCandidate(digit: Int) {
        if (digit !in 1..9)
            return

        if (digit in activeCandidates) {
            activeCandidates.remove(digit)
        } else {
            activeCandidates.add(digit)
            candidates.add(digit)
        }
    }

}