package view

import model.Field
import java.awt.event.MouseEvent
import java.awt.event.MouseListener

class MouseClickListener(
    private val field: Field,
    private val onLeftButton: (Field) -> Unit,
    private val onRigthButton: (Field) -> Unit
): MouseListener {
    override fun mousePressed(e: MouseEvent?) {
        when(e?.button){
            MouseEvent.BUTTON1 -> onLeftButton(field)
            MouseEvent.BUTTON2 -> onRigthButton(field)
            MouseEvent.BUTTON3 -> onRigthButton(field)
        }
    }

    override fun mouseClicked(p0: MouseEvent?) {
        TODO("Not yet implemented")
    }

    override fun mouseReleased(p0: MouseEvent?) {
        TODO("Not yet implemented")
    }

    override fun mouseEntered(p0: MouseEvent?) {
        TODO("Not yet implemented")
    }

    override fun mouseExited(p0: MouseEvent?) {
        TODO("Not yet implemented")
    }
}