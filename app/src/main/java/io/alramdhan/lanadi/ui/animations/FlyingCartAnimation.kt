package io.alramdhan.lanadi.ui.animations

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.lerp

@Composable
fun FlyingCartItem(
    startOffset: Offset,
    endOffset: Offset,
    startSize: IntSize,
    targetSize: IntSize,
    onAnimationFinished: () -> Unit,
) {
    val animProgress = remember { Animatable(0f) }

    LaunchedEffect(Unit) {
        animProgress.animateTo(
            targetValue = 1f,
            animationSpec = tween(800, easing = FastOutSlowInEasing)
        )
        onAnimationFinished()
    }

    val x = androidx.compose.ui.util.lerp(startOffset.x, endOffset.x - 15, animProgress.value)
    val y = androidx.compose.ui.util.lerp(startOffset.y - 20, endOffset.y / 2, animProgress.value)

    val currentWidth = lerp(startSize.width.dp - 15.dp, targetSize.width.dp / 2, animProgress.value)
    val currentHeight = lerp(startSize.height.dp, targetSize.height.dp / 2, animProgress.value)

    Box(
        modifier = Modifier.offset { IntOffset(x.toInt(), y.toInt()) }
            .size(currentWidth, currentHeight)
            .background(Color.Red)
    )
}

