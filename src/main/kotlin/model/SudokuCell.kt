package model

class SudokuCell {

    var value: Int? = null
    var isMutable: Boolean = true
    val candidates = mutableSetOf<Int>()

    fun clearCandidates() {
        candidates.clear()
    }

    fun addCandidate(candidate: Int) {
        candidates += candidate
    }

    fun removeCandidate(candidate: Int) {
        candidates -= candidate
    }

}