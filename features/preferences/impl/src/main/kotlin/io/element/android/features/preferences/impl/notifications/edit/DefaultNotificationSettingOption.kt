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

package io.element.android.features.preferences.impl.notifications.edit
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import io.element.android.features.preferences.impl.R
import io.element.android.libraries.designsystem.components.list.ListItemContent
import io.element.android.libraries.designsystem.preview.ElementPreview
import io.element.android.libraries.designsystem.preview.PreviewsDayNight
import io.element.android.libraries.designsystem.theme.components.ListItem
import io.element.android.libraries.designsystem.theme.components.Text
import io.element.android.libraries.matrix.api.room.RoomNotificationMode

@Composable
fun DefaultNotificationSettingOption(
    mode: RoomNotificationMode,
    onSelectOption: (RoomNotificationMode) -> Unit,
    displayMentionsOnlyDisclaimer: Boolean,
    modifier: Modifier = Modifier,
    isSelected: Boolean = false,
) {
    val title = when (mode) {
        RoomNotificationMode.ALL_MESSAGES -> stringResource(id = R.string.screen_notification_settings_edit_mode_all_messages)
        RoomNotificationMode.MENTIONS_AND_KEYWORDS_ONLY -> stringResource(id = R.string.screen_notification_settings_edit_mode_mentions_and_keywords)
        else -> ""
    }
    val subtitle = when {
        mode == RoomNotificationMode.MENTIONS_AND_KEYWORDS_ONLY && displayMentionsOnlyDisclaimer -> {
            stringResource(id = R.string.screen_notification_settings_mentions_only_disclaimer)
        }
        else -> null
    }
    ListItem(
        modifier = modifier,
        headlineContent = { Text(title) },
        supportingContent = subtitle?.let { { Text(it) } },
        trailingContent = ListItemContent.RadioButton(selected = isSelected),
        onClick = { onSelectOption(mode) },
    )
}

@PreviewsDayNight
@Composable
internal fun DefaultNotificationSettingOptionPreview() = ElementPreview {
    Column {
        DefaultNotificationSettingOption(
            mode = RoomNotificationMode.ALL_MESSAGES,
            isSelected = true,
            displayMentionsOnlyDisclaimer = false,
            onSelectOption = {},
        )
        DefaultNotificationSettingOption(
            mode = RoomNotificationMode.MENTIONS_AND_KEYWORDS_ONLY,
            isSelected = false,
            displayMentionsOnlyDisclaimer = false,
            onSelectOption = {},
        )
        DefaultNotificationSettingOption(
            mode = RoomNotificationMode.MENTIONS_AND_KEYWORDS_ONLY,
            isSelected = false,
            displayMentionsOnlyDisclaimer = true,
            onSelectOption = {},
        )
    }
}
