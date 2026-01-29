package io.alramdhan.lanadi.ui.home

import android.widget.Toast
import androidx.compose.animation.core.tween
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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.NoteAlt
import androidx.compose.material3.Badge
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
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.flowWithLifecycle
import androidx.navigation.NavController
import io.alramdhan.lanadi.core.constants.AppString
import io.alramdhan.lanadi.domain.models.CartProduk
import io.alramdhan.lanadi.navigation.Screen
import io.alramdhan.lanadi.ui.animations.FlyingItemAnimation
import io.alramdhan.lanadi.ui.theme.Typography
import io.alramdhan.lanadi.ui.components.KategoriItem
import io.alramdhan.lanadi.ui.components.LanadiTextField
import io.alramdhan.lanadi.ui.components.ProductItem
import io.alramdhan.lanadi.ui.home.cart.CartEffect
import io.alramdhan.lanadi.ui.home.cart.CartIntent
import io.alramdhan.lanadi.ui.home.cart.CartState
import io.alramdhan.lanadi.ui.theme.Danger
import io.alramdhan.lanadi.viewmodels.home.HomeViewModel
import io.alramdhan.lanadi.viewmodels.home.cart.CartViewModel
import kotlinx.coroutines.launch

@Composable
fun HomeScreen(
    widthSizeClass: WindowWidthSizeClass?,
    navController: NavController,
    viewModel: HomeViewModel,
    cartViewModel: CartViewModel
) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    val state by viewModel.uiState.collectAsState()
    val cartState by cartViewModel.uiState.collectAsState()

    LaunchedEffect(lifecycleOwner) {
        launch {
            viewModel.effect.flowWithLifecycle(lifecycleOwner.lifecycle).collect { effect ->
                when(effect) {
                    is HomeEffect.ShowToastMessage -> Toast.makeText(context, effect.message, Toast.LENGTH_SHORT).show()
                }
            }
        }

        launch {
            cartViewModel.effect.flowWithLifecycle(lifecycleOwner.lifecycle).collect { effect ->
                when(effect) {
                    is CartEffect.ShowToast -> Toast.makeText(context, effect.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    when(widthSizeClass) {
        WindowWidthSizeClass.Compact -> MobileHomeLayout(
            navController,
            viewModel,
            cartViewModel,
            state,
            cartState
        )
        WindowWidthSizeClass.Medium -> TabletHomeLayout(navController)
        WindowWidthSizeClass.Expanded -> TabletHomeLayout(navController)
    }
}

@Composable
private fun MobileHomeLayout(
    navController: NavController,
    viewModel: HomeViewModel,
    cartViewModel: CartViewModel,
    state: HomeState,
    cartState: CartState
) {
    var cartIconCoords by remember { mutableStateOf(Offset.Zero) }

    Box(Modifier.fillMaxSize()) {
        Column {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = AppString.APP_NAME,
                    fontSize = Typography.titleLarge.fontSize
                )
                Box {
                    IconButton(
                        modifier = Modifier
                            .onGloballyPositioned {
                                cartIconCoords = it.positionInRoot()
                                cartViewModel.onIntent(CartIntent.UpdateCartPosition(cartIconCoords))
                            },
                        onClick = { navController.navigate(Screen.Cart.route) }
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.NoteAlt,
                            contentDescription = "",
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                    if(cartState.products.isNotEmpty()) {
                        Badge(Modifier.background(color = Color.Red, CircleShape)) {
                            Text("${cartState.products.size}")
                        }
                    }
                }
            }
            SearchTextField(state, viewModel)
            Spacer(Modifier.height(8.dp))
            ListRowKategori(state, viewModel)
            Spacer(Modifier.height(16.dp))
            ListGridProduk(state, cartViewModel)
        }
        cartState.flyingItems.forEach { fi ->
            FlyingItemAnimation(fi) { cartViewModel.onIntent(CartIntent.AnimationFinished(it)) }
        }
    }
}

@Composable
private fun TabletHomeLayout(navController: NavController) {

}

@Composable
private fun SearchTextField(state: HomeState, viewModel: HomeViewModel) {
    LanadiTextField(
        modifier = Modifier.padding(horizontal = 16.dp),
        value = state.searchText ?: "",
        onValueChange = { viewModel.onIntent(HomeIntent.SearchTextChanged(it)) },
        label = "Search",
        trailingIcon = {
            if(state.searchText != null) {
                IconButton({ viewModel.onIntent(HomeIntent.ResetSearch) }) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "search icon",
                        tint = Danger
                    )
                }
            }
        },
    )
}

@Composable
private fun ListRowKategori(state: HomeState, viewModel: HomeViewModel) {
    LazyRow(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        contentPadding = PaddingValues(horizontal = 16.dp)
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
private fun ListGridProduk(state: HomeState, cartViewModel: CartViewModel) {
    Column(Modifier.padding(horizontal = 16.dp)) {
        Text("Daftar Menu",
            style = TextStyle(color = Color.Gray)
        )
        Spacer(Modifier.height(4.dp))
        LazyVerticalStaggeredGrid(
            columns = StaggeredGridCells.Fixed(2),
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(bottom = 70.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalItemSpacing = 16.dp
        ) {
            when(state.isProdukLoading) {
                true -> items(10) {
                    ProductItem(isLoading = true, produk = null) { _, _, _ -> }
                }
                else -> items(
                    items = state.filterProduks.ifEmpty { state.produks },
                    key = { it.id }
                ) { produk ->
                    ProductItem(modifier = Modifier.animateItem(placementSpec = tween(500)), produk = produk) { offset, size, painter ->
                        cartViewModel.onIntent(CartIntent.AddItem(
                            item = CartProduk(
                                productId = produk.id,
                                name = produk.namaProduk,
                                deskripsi = produk.deskripsi,
                                price = produk.harga.toDouble(),
                                quantity = 1,
                                imageUrl = produk.image
                            ),
                            startPosition = offset,
                            startSize = size,
                            painter = painter
                        ))
                    }
                }
            }
        }
    }
}