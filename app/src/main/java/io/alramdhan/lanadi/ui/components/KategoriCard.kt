package io.alramdhan.lanadi.ui.components

import io.alramdhan.lanadi.R
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import io.alramdhan.lanadi.domain.models.Kategori

@Composable
fun KategoriItem(
    isLoading: Boolean = false,
    kategori: Kategori? = null,
    selected: Boolean = false,
    firstIndexed: Boolean = false,
    onKategoriSelected: () -> Unit
) {
    Card(
        modifier = Modifier
            .padding(end = if(firstIndexed) 16.dp else 0.dp)
            .width(100.dp)
            .height(125.dp),
        colors = CardColors(
            containerColor = if(selected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surface,
            disabledContainerColor = Color.Gray,
            contentColor = if(selected) Color.White else Color.Black,
            disabledContentColor = Color.Black
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        onClick = onKategoriSelected
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            // Placeholder Gambar
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(88.dp)
                    .background(MaterialTheme.colorScheme.secondaryContainer),
                contentAlignment = Alignment.Center
            ) {
                when(isLoading) {
                    true -> SkeletonPlaceholder(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(88.dp),
                        roundedShape = RoundedCornerShape(topStart = 12.dp, topEnd = 12.dp)
                    )
                    else -> {
                        if(firstIndexed) {
                            Image(painterResource(R.drawable.noimage), contentDescription = "image semua kategori")
                        } else {
                            AsyncImage(
                                model = ImageRequest.Builder(LocalContext.current)
                                    .data(kategori!!.image)
                                    .crossfade(true)
                                    .build(),
                                contentDescription = "image kategori ${kategori.id}",
                                contentScale = ContentScale.Crop
                            )
                        }
                    }
                }
            }
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 6.dp, vertical = 4.dp),
            ) {
                when(isLoading) {
                    true ->  SkeletonPlaceholder(modifier = Modifier.width(60.dp))
                    else -> Text(
                        text = kategori?.name ?: "Semua",
                        style = MaterialTheme.typography.bodySmall,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}