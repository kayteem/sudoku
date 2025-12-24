package model

class PencilMarks {

    private val marks: MutableSet<Int> = mutableSetOf()

    fun toggle(digit: Int) {
        require(digit in 1..9) { "Pencil mark must be between 1 and 9" }

        if (!marks.add(digit)) {
            marks.remove(digit)
        }
    }

    fun clear() {
        marks.clear()
    }

    fun contains(digit: Int): Boolean =
        digit in marks

    fun asSortedList(): List<Int> =
        marks.sorted()

    fun isEmpty(): Boolean =
        marks.isEmpty()
}
