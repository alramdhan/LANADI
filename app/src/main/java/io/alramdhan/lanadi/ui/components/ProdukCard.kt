package io.alramdhan.lanadi.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.Comment
import androidx.compose.material.icons.filled.AddShoppingCart
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButtonColors
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.dropShadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.shadow.Shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInRoot
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import io.alramdhan.lanadi.core.utils.toRupiah
import io.alramdhan.lanadi.domain.models.Produk

@Composable
fun ProductItem(isLoading: Boolean = false, produk: Produk?, onTapProdukCard: (Offset, IntSize, Painter) -> Unit) {
    var currentOffset by remember { mutableStateOf(Offset.Zero) }
    var currentSize by remember { mutableStateOf(IntSize.Zero) }
    val painter = if(produk != null) rememberAsyncImagePainter(model = produk.image) else null

    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardColors(
            containerColor = MaterialTheme.colorScheme.surface,
            disabledContainerColor = Color.Gray,
            contentColor = Color.Black,
            disabledContentColor = Color.Black
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        onClick = {}
    ) {
        Column {
            // Placeholder Gambar
            Box(
                modifier = Modifier.onGloballyPositioned {
                        currentOffset = it.positionInRoot()
                        currentSize = it.size
                    }
                    .fillMaxWidth()
                    .height(120.dp)
                    .background(MaterialTheme.colorScheme.secondaryContainer),
                contentAlignment = Alignment.Center
            ) {
                when(isLoading) {
                    true -> SkeletonPlaceholder(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(120.dp),
                        roundedShape = RoundedCornerShape(topStart = 12.dp, topEnd = 12.dp)
                    )
                    else -> {
                        AsyncImage(
                            model = ImageRequest.Builder(LocalContext.current)
                                .data(produk!!.image)
                                .crossfade(true)
                                .build(),
                            contentDescription = "image kategori ${produk.id}",
                            contentScale = ContentScale.Crop,
                        )
                        Box(
                            Modifier
                                .padding(8.dp)
                                .background(Color.White, RoundedCornerShape(8.dp))
                                .padding(horizontal = 8.dp, vertical = 4.dp)
                                .align(Alignment.TopEnd)
                        ) {
                            Text(
                                text = produk.harga.toRupiah(),
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.tertiary,
                                fontWeight = FontWeight.SemiBold
                            )
                        }
                    }
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
            when(isLoading) {
                true -> BodyCardContent {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        SkeletonPlaceholder(Modifier.width(128.dp))
                        SkeletonPlaceholder(Modifier.size(24.dp), roundedShape = CircleShape)
                    }
                    SkeletonPlaceholder(Modifier.width(64.dp))
                    Spacer(Modifier.height(16.dp))
                    SkeletonPlaceholder(
                        Modifier
                            .fillMaxWidth()
                            .height(30.dp))
                }
                else -> BodyCardContent {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.Top
                    ) {
                        Text(
                            modifier = Modifier.weight(1f),
                            text = produk!!.namaProduk,
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            maxLines = 2,
                            overflow = TextOverflow.Visible,
                            lineHeight = 20.sp
                        )
                        FilledIconButton(
                            modifier = Modifier
                                .dropShadow(
                                    CircleShape,
                                    Shadow(
                                        radius = 8.dp,
                                        spread = (-4).dp,
                                        offset = DpOffset(0.dp, 6.dp),
                                        color = Color.Black,
                                        alpha = .5f
                                    )
                                )
                                .size(24.dp),
                            colors = IconButtonColors(
                                containerColor = Color.White,
                                contentColor = Color.Black,
                                disabledContentColor = Color.Black,
                                disabledContainerColor = Color.Gray
                            ),
                            shape = CircleShape,
                            onClick = {}
                        ) {
                            Icon(
                                Icons.Filled.FavoriteBorder,
                                contentDescription = "icon favorit",
                                modifier = Modifier
                                    .padding(4.dp)
                                    .align(Alignment.CenterVertically)
                            )
                        }
                    }
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = "4.4",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.tertiary
                        )
                        Spacer(Modifier.width(4.dp))
                        Icon(
                            Icons.AutoMirrored.Outlined.Comment,
                            contentDescription = "comment",
                            modifier = Modifier.size(14.dp),
                            tint = Color.Gray
                        )
                        Text(
                            "61",
                            style = MaterialTheme.typography.bodySmall,
                        )
                    }
                    Spacer(Modifier.height(8.dp))
                    Text(
                        "2 tersedia",
                        style = MaterialTheme.typography.bodySmall
                    )
                    Spacer(Modifier.height(16.dp))
                    LanadiButton(
                        modifier = Modifier.fillMaxWidth(),
                        onClick = { onTapProdukCard(currentOffset, currentSize, painter!!) },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.secondaryContainer,
                            contentColor = MaterialTheme.colorScheme.tertiary
                        ),
                        content = {
                            Icon(
                                Icons.Default.AddShoppingCart,
                                contentDescription = "ic add cart",
                                tint = MaterialTheme.colorScheme.tertiary,
                            )
                        }
                    )
                }
            }
        }
    }
}

@Composable
private fun BodyCardContent(content: @Composable (ColumnScope.() -> Unit)) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                start = 10.dp,
                end = 10.dp,
                bottom = 14.dp
            ),
        verticalArrangement = Arrangement.spacedBy(4.dp),
        content = content
    )
}