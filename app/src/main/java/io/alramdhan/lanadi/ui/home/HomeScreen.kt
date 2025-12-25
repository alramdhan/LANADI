package io.alramdhan.lanadi.ui.home

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
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.NoteAlt
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import io.alramdhan.lanadi.data.Product
import io.alramdhan.lanadi.data.dummyProducts
import io.alramdhan.lanadi.navigation.Screen
import io.alramdhan.lanadi.ui.theme.Typography
import io.alramdhan.lanadi.ui.widgets.LanadiTextField

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

}

@Composable
private fun ListGridProduk() {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = Modifier.fillMaxSize(),
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
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(modifier = Modifier.padding(8.dp)) {
            // Placeholder Gambar
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp)
                    .background(MaterialTheme.colorScheme.secondaryContainer),
                contentAlignment = Alignment.Center
            ) {
                Text("Gambar")
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