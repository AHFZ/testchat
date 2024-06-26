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

package io.element.android.libraries.matrix.impl.analytics

import im.vector.app.features.analytics.plan.JoinedRoom
import io.element.android.libraries.matrix.api.room.MatrixRoom

private fun Long?.toAnalyticsRoomSize(): JoinedRoom.RoomSize {
    return when (this) {
        null,
        2L -> JoinedRoom.RoomSize.Two
        in 3..10 -> JoinedRoom.RoomSize.ThreeToTen
        in 11..100 -> JoinedRoom.RoomSize.ElevenToOneHundred
        in 101..1000 -> JoinedRoom.RoomSize.OneHundredAndOneToAThousand
        else -> JoinedRoom.RoomSize.MoreThanAThousand
    }
}

fun MatrixRoom.toAnalyticsJoinedRoom(trigger: JoinedRoom.Trigger?): JoinedRoom {
    return JoinedRoom(
        isDM = isDirect,
        isSpace = isSpace,
        roomSize = joinedMemberCount.toAnalyticsRoomSize(),
        trigger = trigger
    )
}
