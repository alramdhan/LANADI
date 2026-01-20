package io.alramdhan.lanadi.ui.home

import android.annotation.SuppressLint
import android.util.Log
import android.widget.Toast
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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.NoteAlt
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInRoot
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import io.alramdhan.lanadi.core.constants.AppString
import io.alramdhan.lanadi.ui.animations.FlyingCartItem
import io.alramdhan.lanadi.ui.home.produk.ProdukIntent
import io.alramdhan.lanadi.ui.home.produk.ProdukState
import io.alramdhan.lanadi.ui.theme.Typography
import io.alramdhan.lanadi.ui.widgets.KategoriItem
import io.alramdhan.lanadi.ui.widgets.LanadiTextField
import io.alramdhan.lanadi.ui.widgets.ProductItem
import io.alramdhan.lanadi.viewmodels.home.HomeViewModel

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun HomeScreen(
    widthSizeClass: WindowWidthSizeClass?,
    navController: NavController,
    viewModel: HomeViewModel,
) {
    val context = LocalContext.current
    val state by viewModel.uiState.collectAsState()
    val produkState by viewModel.produkState.collectAsState()

    LaunchedEffect(key1 = true) {
        viewModel.effect.collect { effect ->
            when(effect) {
                is HomeEffect.ShowToastMessage -> Toast.makeText(context, effect.message, Toast.LENGTH_SHORT).show()
            }
        }
    }

    when(widthSizeClass) {
        WindowWidthSizeClass.Compact -> MobileHomeLayout(navController, viewModel, state, produkState)
        WindowWidthSizeClass.Medium -> TabletHomeLayout(navController)
        WindowWidthSizeClass.Expanded -> TabletHomeLayout(navController)
    }
}

@Composable
private fun MobileHomeLayout(navController: NavController, viewModel: HomeViewModel, state: HomeState, produkState: ProdukState) {
    var cartIconCoords by remember { mutableStateOf(Offset.Zero) }
    Box(Modifier.fillMaxSize()) {
        Column(Modifier.padding(horizontal = 16.dp)) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = AppString.APP_NAME,
                    fontSize = Typography.titleLarge.fontSize
                )
                IconButton(
                    modifier = Modifier
                        .onGloballyPositioned { cartIconCoords = it.positionInRoot() },
                    onClick = {}
                ) {
                    Icon(
                        imageVector = Icons.Outlined.NoteAlt,
                        contentDescription = "",
                        tint = MaterialTheme.colorScheme.primary
                    )
                }
            }
            SearchTextField(state, viewModel)
            Spacer(Modifier.height(8.dp))
            ListRowKategori(state, viewModel)
            Spacer(Modifier.height(16.dp))
            ListGridProduk(state, viewModel)
        }
        if(produkState.isAnimating) {
            FlyingCartItem(
                startOffset = produkState.startCoords,
                endOffset = cartIconCoords,
                onAnimationFinished = { viewModel.onProdukIntent(ProdukIntent.AnimationFinished) }
            )
        }
    }
}

@Composable
private fun TabletHomeLayout(navController: NavController) {

}

@Composable
private fun SearchTextField(state: HomeState, viewModel: HomeViewModel) {
    LanadiTextField(
        value = state.searchMenu ?: "",
        onValueChange = { viewModel.onIntent(HomeIntent.SearchTextChanged(it)) },
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
private fun ListRowKategori(state: HomeState, viewModel: HomeViewModel) {
    LazyRow(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        when(state.isKategoriLoading) {
            true -> items(5) {
                KategoriItem(true, null, false) {}
            }
            else -> items(state.kategoris.size) {
                val kategori = state.kategoris[it]
                KategoriItem(
                    kategori = kategori,
                    selected = state.selectedKategori == kategori.id,
                    onKategoriSelected = { viewModel.onIntent(HomeIntent.OnSelectKategori(kategori.id)) }
                )
            }
        }
    }
}

@Composable
private fun ListGridProduk(state: HomeState, viewModel: HomeViewModel) {
    Column {
        Text("Daftar Menu",
            style = TextStyle(color = Color.Gray)
        )
        Spacer(Modifier.height(4.dp))
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(bottom = 70.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            when(state.isProdukLoading) {
                true -> items(10) {
                    ProductItem(true, null) {}
                }
                else -> items(state.produks.size) { index ->
                    val produk = state.produks[index]
                    ProductItem(produk = produk) { offset ->
                        Log.d("FLying cart", "off $offset")
                        viewModel.onProdukIntent(ProdukIntent.AddToCart(produk, offset))
                    }
                }
            }
        }
    }
}