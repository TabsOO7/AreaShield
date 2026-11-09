package com.tabeni.areashield.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke

@Composable
fun AreaShieldBackground(
    modifier: Modifier = Modifier,
    content: @Composable BoxScope.() -> Unit
) {
    val isDark = MaterialTheme.colorScheme.background.red < 0.5f

    val topColor = if (isDark) Color(0xFF001E2C) else Color(0xFFE2F3FF)
    val bottomColor = if (isDark) Color(0xFF000E17) else Color(0xFFF6FBFF)
    val curveColor = if (isDark) Color(0x2A7DD0FF) else Color(0x2A006689)
    val watermarkColor = if (isDark) Color(0x1A7DD0FF) else Color(0x1A006689)

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(topColor, bottomColor)
                )
            )
    ) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            val w = size.width
            val h = size.height

            // Subtle top-right curve
            val path1 = Path().apply {
                moveTo(w * 0.15f, 0f)
                cubicTo(w * 0.45f, h * 0.08f, w * 0.8f, h * 0.02f, w, h * 0.12f)
                lineTo(w, 0f)
                close()
            }
            drawPath(path = path1, color = curveColor)

            // Faint shield watermark in upper-center
            val shieldPath = Path().apply {
                val cx = w * 0.5f
                val cy = h * 0.35f
                val sw = w * 0.45f
                val sh = h * 0.28f

                moveTo(cx, cy - sh / 2)
                lineTo(cx + sw / 2, cy - sh / 3)
                cubicTo(cx + sw / 2, cy + sh / 3, cx + sw / 4, cy + sh / 2, cx, cy + sh / 2)
                cubicTo(cx - sw / 4, cy + sh / 2, cx - sw / 2, cy + sh / 3, cx - sw / 2, cy - sh / 3)
                close()
            }
            drawPath(path = shieldPath, color = watermarkColor, style = Stroke(width = 3f))

            // Subtle bottom curve
            val path2 = Path().apply {
                moveTo(0f, h * 0.88f)
                cubicTo(w * 0.35f, h * 0.82f, w * 0.75f, h * 0.94f, w, h * 0.86f)
                lineTo(w, h)
                lineTo(0f, h)
                close()
            }
            drawPath(path = path2, color = curveColor)
        }

        content()
    }
}
