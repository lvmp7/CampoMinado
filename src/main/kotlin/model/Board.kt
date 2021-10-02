package model

import java.util.*
import kotlin.collections.ArrayList

enum class EventBoard {WIN,LOSE}

class Board (val lineSize:Int, val columnSize:Int, val mineSize:Int){
    private val fields = ArrayList<ArrayList<Field>>()
    private val callbacks = ArrayList<(EventBoard) -> Unit>()

    init {
        generateFields()
        linkNeighbors()
        sortMines()
    }

    private fun generateFields() {
        for (line in 0 until lineSize){
            fields.add(ArrayList())
            for (column in 0 until columnSize) {
                val newField = Field(line, column)
                newField.onEvent(this::verifyWinOrLose)
                fields[line].add(newField)
            }
        }
    }

    private fun linkNeighbors() {
        forEachField { linkNeighbors(it) }
    }

    private fun linkNeighbors(field: Field) {
        val (line,column) = field
        val lines = arrayListOf(line - 1, line, line +1)
        val columns = arrayListOf(line - 1, line, line +1)

        lines.forEach { l ->
            columns.forEach { c ->
                val actual = fields.getOrNull(l)?.getOrNull(c)
                actual?.takeIf { field != it  }?.let { field.addNeighbor(it) }
            }
        }
    }

    private fun sortMines() {
        val generator = Random()

        var sortedLine = -1
        var sortedColumn = -1
        var actualMineSize = 0

        while (actualMineSize < this.mineSize){
            sortedLine = generator.nextInt(lineSize)
            sortedColumn = generator.nextInt(columnSize)

            val sortedField = fields[sortedLine][sortedColumn]
            if (sortedField.secure){
                sortedField.mine()
                actualMineSize++
            }
        }
    }

    private fun objective (): Boolean{
        var playerWin = true
        forEachField { if(!it.objective) playerWin =false }
        return playerWin
    }

    private fun verifyWinOrLose(field: Field, eventField: EventField){
        if (eventField == EventField.EXPLODE){
            callbacks.forEach { it(EventBoard.LOSE) }
        }else if (objective()){
            callbacks.forEach { it(EventBoard.WIN) }
        }
    }

    fun forEachField(callback: (Field) -> Unit){
        fields.forEach { line -> line.forEach(callback) }
    }

    fun onEvent(callback: (EventBoard) -> Unit){
        callbacks.add { callback }
    }

    fun restart(){
        forEachField { it.restart() }
        sortMines()
    }
}