package io.alramdhan.lanadi.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInRoot
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
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
        onClick = { onTapProdukCard(currentOffset, currentSize, painter!!) }
    ) {
        Column(modifier = Modifier
            .onGloballyPositioned { img ->
                currentOffset = img.positionInRoot()
                currentSize = img.size
            }
        ) {
            // Placeholder Gambar
            Box(
                modifier = Modifier
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
                    else -> AsyncImage(
                        model = ImageRequest.Builder(LocalContext.current)
                            .data(produk!!.image)
                            .crossfade(true)
                            .build(),
                        contentDescription = "image kategori ${produk.id}",
                        contentScale = ContentScale.Crop,
                    )
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        start = 10.dp,
                        end = 10.dp,
                        bottom = 14.dp
                    ),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                when(isLoading) {
                    true -> Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        SkeletonPlaceholder(Modifier.width(128.dp))
                        SkeletonPlaceholder(Modifier.width(24.dp))
                    }
                    else -> Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = produk!!.namaProduk,
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold
                        )
                        Icon(Icons.Filled.FavoriteBorder, contentDescription = "icon favorit")
                    }
                }
                when(isLoading) {
                    true -> SkeletonPlaceholder(Modifier.width(64.dp))
                    else -> Text(
                        text = produk!!.harga.toRupiah(),
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            }
        }
    }
}