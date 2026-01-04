package io.alramdhan.lanadi.ui.home

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.NoteAlt
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import io.alramdhan.lanadi.data.Product
import io.alramdhan.lanadi.data.dummyProducts
import io.alramdhan.lanadi.navigation.Screen
import io.alramdhan.lanadi.ui.theme.Typography
import io.alramdhan.lanadi.ui.widgets.LanadiTextField
import kotlinx.coroutines.delay

@Composable
fun HomeScreen(
    widthSizeClass: WindowWidthSizeClass?,
    navController: NavController
) {
    when(widthSizeClass) {
        WindowWidthSizeClass.Compact -> MobileHomeLayout(navController)
        WindowWidthSizeClass.Medium -> TabletHomeLayout(navController)
        WindowWidthSizeClass.Expanded -> TabletHomeLayout(navController)
    }
}

@Composable
private fun MobileHomeLayout(navController: NavController) {
    Column(Modifier.padding(horizontal = 16.dp)) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Menu Menu",
                fontSize = Typography.titleLarge.fontSize
            )
            IconButton({
                navController.navigate(Screen.Login.route)
            }) {
                Icon(imageVector = Icons.Outlined.NoteAlt, contentDescription = "", tint = MaterialTheme.colorScheme.primary)
            }
        }
        SearchTextField()
        Spacer(Modifier.height(16.dp))
        ListRowKategori()
        Spacer(Modifier.height(16.dp))
        ListGridProduk()
    }
}

@Composable
private fun TabletHomeLayout(navController: NavController) {

}

@Composable
private fun SearchTextField() {
    var search by remember { mutableStateOf("") }
    LanadiTextField(
        value = search,
        onValueChange = { search = it },
        label = "Search",
        trailingIcon = {
            IconButton({}) {
                Icon(
                    imageVector = Icons.Filled.Search,
                    contentDescription = "search icon",
                    tint = MaterialTheme.colorScheme.primary
                )
            }
        },
    )
}

@Composable
private fun ListRowKategori() {
    LazyRow(Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(12.dp)) {
        items(5) { index ->
            KategoriItem(index)
        }
    }
}

@Composable
private fun KategoriItem(index: Int) {
    val selectedIndex = 0

    Card(
        modifier = Modifier.size(100.dp),
        colors = CardColors(
            containerColor = if(selectedIndex == index) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surface,
            disabledContainerColor = Color.Gray,
            contentColor = if(selectedIndex == index) Color.White else Color.Black,
            disabledContentColor = Color.Black
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
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
                    .height(60.dp)
                    .background(MaterialTheme.colorScheme.secondaryContainer),
                contentAlignment = Alignment.Center
            ) {
                Text("Gambar")
            }
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Kategori $index",
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

@Composable
private fun ListGridProduk() {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(bottom = 70.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(dummyProducts.size) { index ->
            ProductItem(dummyProducts[index])
        }
    }
}

@Composable
private fun ProductItem(product: Product) {
    val animateItems = remember {
        mutableStateListOf<Product>().apply {
            addAll(dummyProducts)
        }
    }
    val scale = remember { Animatable(0f) }
    val alpha = remember { Animatable(0f) }

    LaunchedEffect(Unit) {
        delay(product.id * 50L)
        scale.animateTo(
            targetValue = 1f,
            animationSpec = spring(
                dampingRatio = Spring.DampingRatioMediumBouncy,
                stiffness = Spring.StiffnessLow
            )
        )
        alpha.animateTo(1f, animationSpec = tween(300))
    }

    Card(
        modifier = Modifier.fillMaxWidth()
            .graphicsLayer {
                scaleX = scale.value
                scaleY = scale.value
                this.alpha = alpha.value
            },
        colors = CardColors(
            containerColor = MaterialTheme.colorScheme.surface,
            disabledContainerColor = Color.Gray,
            contentColor = Color.Black,
            disabledContentColor = Color.Black
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column {
            // Placeholder Gambar
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp)
                    .background(MaterialTheme.colorScheme.secondaryContainer),
                contentAlignment = Alignment.Center
            ) {
                Image(painterResource(product.imageRes), contentDescription = "gambar produk")
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = product.name,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = product.price,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.primary
            )
        }
    }
}