/*
 * Copyright (c) 2024 New Vector Ltd
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.element.android.features.call.utils

import io.element.android.features.call.api.CallType
import io.element.android.features.call.impl.notifications.CallNotificationData
import io.element.android.features.call.impl.utils.ActiveCall
import io.element.android.features.call.impl.utils.ActiveCallManager
import kotlinx.coroutines.flow.MutableStateFlow

class FakeActiveCallManager(
    var registerIncomingCallResult: (CallNotificationData) -> Unit = {},
    var incomingCallTimedOutResult: () -> Unit = {},
    var hungUpCallResult: (CallType) -> Unit = {},
    var joinedCallResult: (CallType) -> Unit = {},
) : ActiveCallManager {
    override val activeCall = MutableStateFlow<ActiveCall?>(null)

    override fun registerIncomingCall(notificationData: CallNotificationData) {
        registerIncomingCallResult(notificationData)
    }

    override fun incomingCallTimedOut() {
        incomingCallTimedOutResult()
    }

    override fun hungUpCall(callType: CallType) {
        hungUpCallResult(callType)
    }

    override fun joinedCall(callType: CallType) {
        joinedCallResult(callType)
    }

    fun setActiveCall(value: ActiveCall?) {
        this.activeCall.value = value
    }
}
