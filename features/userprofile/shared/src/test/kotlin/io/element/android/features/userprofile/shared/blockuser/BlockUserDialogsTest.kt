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

package io.element.android.features.userprofile.shared.blockuser

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import io.element.android.features.userprofile.shared.R
import io.element.android.features.userprofile.shared.UserProfileEvents
import io.element.android.features.userprofile.shared.UserProfileState
import io.element.android.features.userprofile.shared.aUserProfileState
import io.element.android.libraries.ui.strings.CommonStrings
import io.element.android.tests.testutils.EventsRecorder
import io.element.android.tests.testutils.clickOn
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class BlockUserDialogsTest {
    @get:Rule val rule = createAndroidComposeRule<ComponentActivity>()

    @Test
    fun `confirm block user emit expected Event`() {
        val eventsRecorder = EventsRecorder<UserProfileEvents>()
        rule.setContent {
            BlockUserDialogs(
                state = aUserProfileState(
                    displayConfirmationDialog = UserProfileState.ConfirmationDialog.Block,
                    eventSink = eventsRecorder,
                )
            )
        }
        rule.clickOn(R.string.screen_dm_details_block_alert_action)
        eventsRecorder.assertSingle(UserProfileEvents.BlockUser(false))
    }

    @Test
    fun `cancel block user emit expected Event`() {
        val eventsRecorder = EventsRecorder<UserProfileEvents>()
        rule.setContent {
            BlockUserDialogs(
                state = aUserProfileState(
                    displayConfirmationDialog = UserProfileState.ConfirmationDialog.Block,
                    eventSink = eventsRecorder,
                )
            )
        }
        rule.clickOn(CommonStrings.action_cancel)
        eventsRecorder.assertSingle(UserProfileEvents.ClearConfirmationDialog)
    }

    @Test
    fun `confirm unblock user emit expected Event`() {
        val eventsRecorder = EventsRecorder<UserProfileEvents>()
        rule.setContent {
            BlockUserDialogs(
                state = aUserProfileState(
                    displayConfirmationDialog = UserProfileState.ConfirmationDialog.Unblock,
                    eventSink = eventsRecorder,
                )
            )
        }
        rule.clickOn(R.string.screen_dm_details_unblock_alert_action)
        eventsRecorder.assertSingle(UserProfileEvents.UnblockUser(false))
    }

    @Test
    fun `cancel unblock user emit expected Event`() {
        val eventsRecorder = EventsRecorder<UserProfileEvents>()
        rule.setContent {
            BlockUserDialogs(
                state = aUserProfileState(
                    displayConfirmationDialog = UserProfileState.ConfirmationDialog.Unblock,
                    eventSink = eventsRecorder,
                )
            )
        }
        rule.clickOn(CommonStrings.action_cancel)
        eventsRecorder.assertSingle(UserProfileEvents.ClearConfirmationDialog)
    }
}
