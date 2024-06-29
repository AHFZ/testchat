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

package io.element.android.libraries.vero.impl.relation.api

import io.element.android.libraries.vero.api.relation.VeroContact
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable data class SerializedVeroContact(
    @SerialName("id") override val id: String,
    @SerialName("firstname") override val firstname: String,
    @SerialName("lastname") override val lastname: String,
    @SerialName("username") override val username: String,
    //@SerialName("loop") override val loop: String,
    @SerialName("picture") override val picture: String?
) : VeroContact
