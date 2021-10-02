package view

import model.Board
import java.awt.GridLayout
import javax.swing.JPanel


class BoardPanel (board: Board): JPanel() {
    init {
        layout = GridLayout(board.lineSize,board.columnSize)
        board.forEachField { field ->
            val button = FieldButton(field)
            add(button)
        }
    }
}