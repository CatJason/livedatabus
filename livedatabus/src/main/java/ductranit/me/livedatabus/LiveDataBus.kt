/**
* 版权所有 (C) 2018 ductranit
*
* 根据Apache许可证2.0版（"许可证"）授权；
* 除非符合许可证要求，否则不得使用此文件。
* 您可以在以下位置获取许可证副本：
*
*      http://www.apache.org/licenses/LICENSE-2.0
*
* 除非适用法律要求或书面同意，本软件按"原样"分发，
* 没有任何明示或暗示的担保或条件。
* 详见许可证中特定语言规定的权限和限制。
*/

package ductranit.me.livedatabus

import androidx.annotation.NonNull
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer

/**
* 用于管理总线事件的单例对象。
*/
object LiveDataBus {

    private val subjectMap = HashMap<String, EventLiveData>()

    /**
    * 获取LiveData，如果内存中不存在则创建新实例。
    */
    @NonNull
    private fun getLiveData(subjectCode: String): EventLiveData {
        var liveData: EventLiveData? = subjectMap[subjectCode]
        if (liveData == null) {
            liveData = EventLiveData(subjectCode)
            subjectMap[subjectCode] = liveData
        }

        return liveData
    }

    /**
    * 订阅指定主题并监听该主题的更新。
    */
    fun subscribe(subject: String, @NonNull lifecycle: LifecycleOwner, @NonNull action: Observer<ConsumableEvent>) {
        try {
            // 避免重复注册相同实例
            getLiveData(subject).observe(lifecycle, action)
        } catch (throwable: IllegalArgumentException) {
            throwable.printStackTrace()
        }
    }

    /**
    * 当主题没有观察者时移除该主题。
    */
    fun unregister(subject: String) {
        subjectMap.remove(subject)
    }

    /**
    * 向指定主题发布对象，供该主题的所有订阅者接收。
    */
    fun publish(subject: String, message: ConsumableEvent = ConsumableEvent()) {
        getLiveData(subject).update(message)
    }
}