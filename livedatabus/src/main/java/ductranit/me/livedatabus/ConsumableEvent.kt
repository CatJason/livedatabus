package ductranit.me.livedatabus

/**
* 可消费事件，确保事件只能执行一次
* 如果不消费它，事件可能会被多次执行
*/
data class ConsumableEvent(var isConsumed: Boolean = false, var value: Any? = null) {
    /**
    * 执行任务并在之后消费该事件
    */
    fun runAndConsume(runnable: () -> Unit) {
        if (!isConsumed) {
            runnable()
            isConsumed = true
        }
    }
}