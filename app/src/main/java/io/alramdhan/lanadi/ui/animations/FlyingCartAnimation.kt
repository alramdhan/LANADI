package io.alramdhan.lanadi.ui.animations

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.lerp

@Composable
fun FlyingCartItem(
    startOffset: Offset,
    endOffset: Offset,
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

    val x = lerp(startOffset.x, endOffset.x, animProgress.value)
    val y = lerp(startOffset.y, endOffset.y, animProgress.value)

    Box(
        modifier = Modifier.offset { IntOffset(x.toInt(), y.toInt()) }
            .size(40.dp)
            .background(Color.Red, CircleShape)
    )
}

