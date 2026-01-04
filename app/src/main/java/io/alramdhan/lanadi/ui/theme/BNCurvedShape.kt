package io.alramdhan.lanadi.ui.theme

import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.LayoutDirection

class BNCurvedShape(private val cutoutRadius: Float = 140f) : Shape {
    override fun createOutline(
        size: Size,
        layoutDirection: LayoutDirection,
        density: Density
    ): Outline {
        val path = Path().apply {
            val center = size.width / 2

            // start from top left
            moveTo(0f, 0f)
            // garis lurus sampai mendekati cutout
            lineTo(center - cutoutRadius, 0f)
            // gambar kurva cekungan
            // titik kontrol pertama(x1, y1), kedua(x2, y2) dan tujuan (x3, y3)
            cubicTo(
                x1 = center - (cutoutRadius / 2f), y1 = 0f,
                x2 = center - (cutoutRadius / 2f), y2 = cutoutRadius / 1.5f,
                x3 = center, y3 = cutoutRadius / 1.5f
            )
            cubicTo(
                x1 = center + (cutoutRadius / 2f), y1 = cutoutRadius / 1.5f,
                x2 = center + (cutoutRadius / 2f), y2 = 0f,
                x3 = center + cutoutRadius, y3 = 0f
            )

            lineTo(size.width, 0f)
            lineTo(size.width, size.height)
            lineTo(0f, size.height)
            close()
        }
        return Outline.Generic(path)
    }
}