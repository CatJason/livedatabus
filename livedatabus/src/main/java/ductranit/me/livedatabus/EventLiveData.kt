/*
 * Copyright (C) 2018 ductranit
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package ductranit.me.livedatabus

import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer

/**
* 自定义的LiveData，当没有观察者时会自动取消注册
*/
class EventLiveData(private val mSubject: String) : LiveData<ConsumableEvent>() {

    fun update(obj: ConsumableEvent) {
        postValue(obj)
    }

    override fun removeObserver(observer: Observer<in ConsumableEvent>) {
        super.removeObserver(observer)
        if (!hasObservers()) {
            LiveDataBus.unregister(mSubject)
        }
    }
}