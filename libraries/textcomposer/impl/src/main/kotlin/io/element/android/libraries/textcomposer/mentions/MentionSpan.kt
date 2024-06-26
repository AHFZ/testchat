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

package io.element.android.libraries.textcomposer.mentions

import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.RectF
import android.graphics.Typeface
import android.text.style.ReplacementSpan
import io.element.android.libraries.core.extensions.orEmpty
import kotlin.math.min
import kotlin.math.roundToInt

class MentionSpan(
    val text: String,
    val rawValue: String,
    val type: Type,
    val backgroundColor: Int,
    val textColor: Int,
    val startPadding: Int,
    val endPadding: Int,
    val typeface: Typeface = Typeface.DEFAULT,
) : ReplacementSpan() {
    companion object {
        private const val MAX_LENGTH = 20
    }

    private var actualText: CharSequence? = null
    private var textWidth = 0
    private val backgroundPaint = Paint().apply {
        isAntiAlias = true
        color = backgroundColor
    }

    override fun getSize(paint: Paint, text: CharSequence?, start: Int, end: Int, fm: Paint.FontMetricsInt?): Int {
        val mentionText = getActualText(this.text)
        paint.typeface = typeface
        textWidth = paint.measureText(mentionText, 0, mentionText.length).roundToInt()
        return textWidth + startPadding + endPadding
    }

    override fun draw(canvas: Canvas, text: CharSequence?, start: Int, end: Int, x: Float, top: Int, y: Int, bottom: Int, paint: Paint) {
        val mentionText = getActualText(this.text)

        // Extra vertical space to add below the baseline (y). This helps us center the span vertically
        val extraVerticalSpace = y + paint.ascent() + paint.descent() - top

        val rect = RectF(x, top.toFloat(), x + textWidth + startPadding + endPadding, y.toFloat() + extraVerticalSpace)
        val radius = rect.height() / 2
        canvas.drawRoundRect(rect, radius, radius, backgroundPaint)
        paint.color = textColor
        paint.typeface = typeface
        canvas.drawText(mentionText, 0, mentionText.length, x + startPadding, y.toFloat(), paint)
    }

    private fun getActualText(text: String): CharSequence {
        if (actualText != null) return actualText!!
        return buildString {
            val mentionText = text.orEmpty()
            when (type) {
                Type.USER -> {
                    if (text.firstOrNull() != '@') {
                        append("@")
                    }
                }
                Type.ROOM -> {
                    if (text.firstOrNull() != '#') {
                        append("#")
                    }
                }
            }
            append(mentionText.substring(0, min(mentionText.length, MAX_LENGTH)))
            if (mentionText.length > MAX_LENGTH) {
                append("…")
            }
            actualText = this
        }
    }

    enum class Type {
        USER,
        ROOM,
    }
}
