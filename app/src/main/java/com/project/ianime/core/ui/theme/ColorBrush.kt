package com.project.ianime.core.ui.theme

import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.colorspace.ColorSpaces
import kotlin.math.cos
import kotlin.math.sin

private fun oklch(l: Float, c: Float, hueDegrees: Float): Color {
    val h = Math.toRadians(hueDegrees.toDouble())
    return Color(
        l,
        (c * cos(h)).toFloat(),
        (c * sin(h)).toFloat(),
        colorSpace = ColorSpaces.Oklab
    ).convert(ColorSpaces.Srgb)
}

fun coverBrush(seed: String): Brush {
    val hue = seed.hashCode().mod(360).toFloat()
    return Brush.verticalGradient(
        listOf(
            oklch(0.88f, 0.07f, hue),
            oklch(0.78f, 0.11f, hue + 50f)
        )
    )
}
