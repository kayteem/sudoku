package app

import javafx.application.Application

class Main {
    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            Application.launch(SudokuApp::class.java, *args)
        }
    }
}