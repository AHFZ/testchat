/*
 * Copyright (c) 2023 New Vector Ltd
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

package io.element.android.features.poll.impl.create

import androidx.compose.runtime.saveable.SaverScope
import com.google.common.truth.Truth.assertThat
import kotlinx.collections.immutable.toPersistentList
import org.junit.Test

class PollFormStateSaverTest {
    companion object {
        val CanSaveScope = SaverScope { true }
    }

    @Test
    fun `test save and restore`() {
        val state = PollFormState(
            question = "question",
            answers = listOf("answer1", "answer2").toPersistentList(),
            isDisclosed = true,
        )

        val saved = with(CanSaveScope) {
            with(pollFormStateSaver) {
                save(state)
            }
        }

        val restored = saved?.let {
            pollFormStateSaver.restore(it)
        }

        assertThat(restored).isEqualTo(state)
    }
}