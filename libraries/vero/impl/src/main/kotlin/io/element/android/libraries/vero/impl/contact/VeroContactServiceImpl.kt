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

package io.element.android.libraries.vero.impl.contact

import com.squareup.anvil.annotations.ContributesBinding
import io.element.android.libraries.di.AppScope
import io.element.android.libraries.vero.api.contact.VeroContactService
import io.element.android.libraries.vero.api.contact.VeroContacts
import io.element.android.libraries.vero.impl.contact.api.VeroContactAPI
import io.element.android.libraries.vero.impl.contact.store.VeroContactStore
import io.element.android.libraries.vero.impl.util.toBearerToken
import javax.inject.Inject

@ContributesBinding(AppScope::class)
class VeroContactServiceImpl @Inject constructor(
    private val veroContactAPI: VeroContactAPI,
    private val veroContactStore: VeroContactStore
) : VeroContactService {

    override suspend fun syncContact(token: String) {
        veroContactStore.deleteAllContact()
        val contact = veroContactAPI.getContact(token.toBearerToken()).body()
        veroContactStore.insertContacts(contact ?: return)
    }

    override suspend fun getContact(query: String?): VeroContacts {
        return veroContactStore.getContacts(query)
    }
}
