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

    override fun mouseClicked(e: MouseEvent?) {}
    override fun mouseReleased(e: MouseEvent?) {}
    override fun mouseEntered(e: MouseEvent?) {}
    override fun mouseExited(e: MouseEvent?) {}
}