package ui

import controller.SudokuController
import javafx.geometry.Pos
import javafx.scene.input.MouseButton
import javafx.scene.layout.GridPane
import javafx.scene.paint.Color
import javafx.scene.text.Font
import javafx.scene.text.FontWeight
import javafx.scene.text.Text
import model.GameMode
import model.SudokuCell

class CandidatesGridView(
    private val controller: SudokuController,
    private val cell: SudokuCell,
    private val updateParentCallback: () -> Unit
) : GridPane() {

    private val FONT_CANDIDATE: Font = Font.font("System", FontWeight.NORMAL, 14.0)

    init {
        hgap = 6.0
        vgap = 3.0
        prefWidth = 50.0
        prefHeight = 50.0
        alignment = Pos.CENTER
    }

    fun populate() {
        println("populating candidates grid...")
        children.clear()

        for (i in 1..9) {
            createGridCell(i)
        }

        isVisible = true
    }

    fun hide() {
        println("hiding candidates grid...")

        isVisible = false
        children.clear()
    }

    // helpers
    private fun createGridCell(i: Int) {
        val label = Text(i.toString())
        label.font = FONT_CANDIDATE
        label.fill = if (i in cell.candidates) Color.BLACK else Color.DARKGRAY

        val r = (i - 1) / 3
        val c = (i - 1) % 3
        add(label, c, r)

        label.setOnMouseReleased { mouseEvent ->
            println("clicked candidate $i")

            when (controller.mode) {
                GameMode.CREATION -> {
                    cell.value = i
                    cell.isMutable = false
                }
                GameMode.RIDDLE -> {
                    if (!cell.isMutable)
                        return@setOnMouseReleased

                    when (mouseEvent.button) {
                        MouseButton.PRIMARY -> {
                            if (cell.candidates.contains(i)) {
                                cell.value = i
                                cell.isMutable = true
                            }
                            else {
                                cell.addCandidate(i)
                            }
                        }
                        MouseButton.SECONDARY -> {
                            cell.removeCandidate(i)
                        }
                        else -> {}
                    }
                }
            }

            updateParentCallback()
            mouseEvent.consume()
        }
    }

}
