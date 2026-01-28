package io.alramdhan.lanadi.ui.animations

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.lerp
import kotlin.math.roundToInt

@Composable
fun FlyingItemAnimationBAK(
    startOffset: Offset,
    endOffset: Offset,
    painter: Painter? = null,
    onAnimationFinished: () -> Unit,
) {
    val animProgress = remember { Animatable(0f) }

    LaunchedEffect(Unit) {
        animProgress.animateTo(
            targetValue = 1f,
            animationSpec = tween(3000, easing = FastOutSlowInEasing)
        )
        onAnimationFinished()
    }

//    val x = androidx.compose.ui.util.lerp(startOffset.x, endOffset.x - 15, animProgress.value)
//    val y = androidx.compose.ui.util.lerp(startOffset.y - 20, endOffset.y / 2, animProgress.value)

//    val currentWidth = lerp(startSize.width.dp - 15.dp, targetSize.width.dp / 2, animProgress.value)
//    val currentHeight = lerp(startSize.height.dp, targetSize.height.dp / 2, animProgress.value)

//    Box(
//        modifier = Modifier.offset { IntOffset(x.toInt(), y.toInt()) }
//            .size(currentWidth, currentHeight)
//            .background(Color.Red)
//    )

    val x by animateDpAsState(
        targetValue = lerp(startOffset.x.dp, endOffset.x.dp, animProgress.value),
        label = "offset x"
    )
    val y by animateDpAsState(
        targetValue = lerp(startOffset.y.dp, endOffset.y.dp, animProgress.value),
        label = "offset y"
    )
    val scale by animateFloatAsState(
        targetValue = androidx.compose.ui.util.lerp(1f, 0.2f, animProgress.value),
        label = "scale animation"
    )

    Image(
        painter = painter!!,
        contentDescription = "",
        modifier = Modifier
            .offset(x, y)
            .size(80.dp)
            .graphicsLayer {
                scaleX = scale
                scaleY = scale
            }
    )
}

@Composable
fun FlyingItemAnimation(
    item: FlyingItem,
    onAnimationFinishId: (Long) -> Unit
) {
    val density = LocalDensity.current
    val startWidth = with(density) { item.startSize.width.toDp() }
    val startHeight = with(density) { item.startSize.height.toDp() }
    val targetSizeDp = 24.dp
    val animationProgress = remember { Animatable(0f) }

    LaunchedEffect(item) {
        animationProgress.animateTo(
            targetValue = 1f,
            animationSpec = tween(1000, easing = FastOutSlowInEasing)
        )

        onAnimationFinishId(item.id)
    }
    val progress = animationProgress.value
    val currentX = item.startPosition.x + (item.targetPosition.x - item.startPosition.x) * progress
    val currentY = item.startPosition.y + (item.targetPosition.y - item.startPosition.y) * progress

    val currentWidth = lerp(startWidth, targetSizeDp, progress)
    val currentHeight = lerp(startHeight, targetSizeDp, progress)

    Image(
        painter = item.image!!,
        contentDescription = "flying image",
        contentScale = ContentScale.Crop,
        modifier = Modifier
            .offset { IntOffset(currentX.roundToInt(), currentY.roundToInt()) }
            .size(currentWidth, currentHeight)
    )
}

