package view

import model.EventField
import model.Field
import java.awt.Color
import java.awt.Font
import javax.swing.BorderFactory
import javax.swing.JButton
import javax.swing.SwingUtilities

private val DEFAULT_COLOR_BUTTON = Color(184,184,184)
private val DEFAULT_COLOR_MARKED = Color(8,179,247)
private val DEFAULT_COLOR_EXPLOSION = Color(189,66,68)
private val DEFAULT_TXT_COLOR = Color(0,100,0)

class FieldButton(private val field:Field):JButton() {
    init {
        font = font.deriveFont(Font.BOLD)
        background = DEFAULT_COLOR_BUTTON
        isOpaque = true
        border = BorderFactory.createBevelBorder(0)

        addMouseListener(MouseClickListener(field,{it.open()},{it.changeMarked()}))

        field.onEvent(this::applyStyle)
    }

    private fun applyStyle(field: Field, event: EventField){
        when(event){
            EventField.EXPLODE -> applyExplodedStyle()
            EventField.OPEN -> applyOpenedStyle()
            EventField.MARKED -> applyMarkedStyle()
            else -> applyDefaultStyle()
        }

        SwingUtilities.invokeLater {
            repaint()
            validate()
        }
    }

    private fun applyExplodedStyle() {
        background = DEFAULT_COLOR_EXPLOSION
        text = "X"
    }

    private fun applyOpenedStyle() {
        background = DEFAULT_COLOR_BUTTON
        border = BorderFactory.createLineBorder(Color.GRAY)

        foreground = when (field.neighborMinedSize){
            1 -> DEFAULT_TXT_COLOR
            2 -> Color.BLUE
            3 -> Color.YELLOW
            4,5,6 -> Color.RED
            else -> Color.PINK
        }

        text = if (field.neighborMinedSize > 0 ) field.neighborMinedSize.toString() else ""
    }

    private fun applyMarkedStyle() {
        background = DEFAULT_COLOR_MARKED
        foreground = Color.BLACK
        text = "M"
    }

    private fun applyDefaultStyle() {
        background = DEFAULT_COLOR_BUTTON
        border = BorderFactory.createBevelBorder(0)
        text = ""
    }
}