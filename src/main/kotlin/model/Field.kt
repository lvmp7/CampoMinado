package model

enum class EventField {OPEN, MARKED, UNMARKED, EXPLODE, RESTART }

data class Field (val line: Int, val column: Int) {
    private val neighbors = ArrayList<Field>()
    private val callbacks = ArrayList<(Field, EventField) -> Unit>()

    var marked = false
    var open = false
    var mined = false

    val unmarked: Boolean get() = !marked
    val closed: Boolean get() = !open
    val secure: Boolean get() = !mined
    val objective: Boolean get() = secure && open || mined && marked
    val neighborMinedSize: Int get() = neighbors.filter { it.mined }.size
    val secureNeighbor:Boolean
        get() = neighbors.map { it.secure }.reduce { result, secure -> result && secure }

    fun addNeighbor(neighbor: Field){
        neighbors.add(neighbor)
    }

    fun onEvent(callback: (Field,EventField) -> Unit){
        callbacks.add(callback)
    }

    fun open(){
        if (closed){
            open = true
            if (mined){
                callbacks.forEach { it(this, EventField.EXPLODE)}
            }else{
                callbacks.forEach { it(this, EventField.OPEN) }
                neighbors.filter { it.closed && it.secure && secureNeighbor }.forEach { it.open() }
            }
        }
    }

    fun changeMarked(){
        if (closed){
            marked = !marked
            val event = if (marked) EventField.MARKED else EventField.UNMARKED
            callbacks.forEach { it(this,event) }
        }
    }

    fun mine(){
        mined = true
    }

    fun restart(){
        marked = false
        open = false
        mined = false
        callbacks.forEach { callback -> callback(this,EventField.RESTART) }
    }
}