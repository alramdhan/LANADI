package io.alramdhan.lanadi.ui.components

import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.AnchoredDraggableDefaults
import androidx.compose.foundation.gestures.AnchoredDraggableState
import androidx.compose.foundation.gestures.DraggableAnchors
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.anchoredDraggable
import androidx.compose.foundation.gestures.animateTo
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalWindowInfo
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import io.alramdhan.lanadi.ui.theme.Danger
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

enum class SwipeState {
    CLOSED,
    OPEN
}

@Composable
fun SwipeToRevealCard(
    onDeleteClick: () -> Unit,
    content: @Composable BoxScope.() -> Unit
) {
    val density = LocalDensity.current
    val size = LocalWindowInfo.current.containerSize
    val screenWidth = size.width
    val actionButtonSize = (screenWidth * 0.12).dp
    val actionButtonSizePx = with(density) { actionButtonSize.toPx() }
    val scope = rememberCoroutineScope()

    val startOffset = 0f
    val endOffset = with(density) { actionButtonSize.toPx() }

    val state = remember {
        AnchoredDraggableState(initialValue = SwipeState.CLOSED)
    }

    SideEffect {
        state.updateAnchors(
            DraggableAnchors {
                SwipeState.CLOSED at 0f
                SwipeState.OPEN at -actionButtonSizePx
            }
        )
    }

    Box(Modifier.fillMaxSize()) {
        Box(
            modifier = Modifier.align(Alignment.CenterEnd)
                .width(actionButtonSize)
                .height(100.dp)
                .padding(horizontal = 8.dp)
                .background(Danger, RoundedCornerShape(12.dp))
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = rememberRipple(),
                    onClick = {
                        scope.launch { state.animateTo(SwipeState.CLOSED) }
                        onDeleteClick()
                    }
                ),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Default.Delete,
                contentDescription = "Hapus",
                tint = Color.White
            )
        }

        Box(
            modifier = Modifier
                .fillMaxSize()
                .offset {
                    IntOffset(
                        x = state.requireOffset().roundToInt(),
                        y = 0
                    )
                }
                .anchoredDraggable(
                    state = state,
                    flingBehavior = AnchoredDraggableDefaults.flingBehavior(
                        state = state,
                        positionalThreshold = { distance: Float -> distance * 0.5f },
                        animationSpec = tween()
                    ),
                    orientation = Orientation.Horizontal
                )
                .background(MaterialTheme.colorScheme.surface), // Background konten utama
            content = content
        )
    }
}