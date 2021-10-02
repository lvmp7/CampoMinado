package view

import model.Board
import model.EventBoard
import javax.swing.JFrame
import javax.swing.JOptionPane
import javax.swing.SwingUtilities

fun main() {
    Application()
}

class Application: JFrame() {

    private val board = Board(lineSize = 16,columnSize = 30,mineSize = 89)
    private val boardPanel = BoardPanel(board)

    init {
        board.onEvent ( this::showResult )
        add(boardPanel)

        setSize(690,438)
        setLocationRelativeTo(null)
        defaultCloseOperation = EXIT_ON_CLOSE
        title = "Campo Minado"
        isVisible = true
    }

    private fun showResult(event: EventBoard){
        SwingUtilities.invokeLater {
            val msg = when(event){
                EventBoard.WIN -> "You WIN"
                EventBoard.LOSE -> "You LOSE.... :P"
            }

            JOptionPane.showMessageDialog(this,msg)
            board.restart()

            boardPanel.repaint()
            boardPanel.validate()
        }
    }
}