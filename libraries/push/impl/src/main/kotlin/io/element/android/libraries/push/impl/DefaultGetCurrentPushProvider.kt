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

package io.element.android.libraries.push.impl

import com.squareup.anvil.annotations.ContributesBinding
import io.element.android.libraries.di.AppScope
import io.element.android.libraries.push.api.GetCurrentPushProvider
import io.element.android.libraries.pushstore.api.UserPushStoreFactory
import io.element.android.services.appnavstate.api.AppNavigationStateService
import io.element.android.services.appnavstate.api.currentSessionId
import javax.inject.Inject

@ContributesBinding(AppScope::class)
class DefaultGetCurrentPushProvider @Inject constructor(
    private val pushStoreFactory: UserPushStoreFactory,
    private val appNavigationStateService: AppNavigationStateService,
) : GetCurrentPushProvider {
    override suspend fun getCurrentPushProvider(): String? {
        return appNavigationStateService
            .appNavigationState
            .value
            .navigationState
            .currentSessionId()
            ?.let { pushStoreFactory.getOrCreate(it) }
            ?.getPushProviderName()
    }
}
