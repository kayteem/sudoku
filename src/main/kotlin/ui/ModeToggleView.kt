package ui

import controller.SudokuController
import javafx.scene.control.ToggleButton
import javafx.scene.layout.HBox
import model.GameMode

class ModeToggleView(
    private val controller: SudokuController
) : HBox() {

    private val toggleButton = ToggleButton()

    init {
        toggleButton.text = "Erstellungs-Modus"
        children.add(toggleButton)

        toggleButton.setOnAction {
            if (toggleButton.isSelected) {
                controller.switchMode(GameMode.RAETSEL)
                toggleButton.text = "Rätsel-Modus"
            } else {
                controller.switchMode(GameMode.ERSTELLUNG)
                toggleButton.text = "Erstellungs-Modus"
            }
        }
    }
}
