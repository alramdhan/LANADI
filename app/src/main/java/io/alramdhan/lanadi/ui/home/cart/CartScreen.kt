package io.alramdhan.lanadi.ui.home.cart

import android.util.Log
import android.widget.Toast
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemColors
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import io.alramdhan.lanadi.core.utils.toRupiah
import io.alramdhan.lanadi.domain.models.CartProduk
import io.alramdhan.lanadi.ui.components.NumberStepper
import io.alramdhan.lanadi.ui.components.SkeletonPlaceholder
import io.alramdhan.lanadi.ui.components.SwipeToRevealCard
import io.alramdhan.lanadi.ui.theme.Danger
import io.alramdhan.lanadi.viewmodels.home.cart.CartViewModel
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CartScreen(
    widthSizeClass: WindowWidthSizeClass?,
    navController: NavController,
    viewModel: CartViewModel = koinViewModel(),
) {
    val context = LocalContext.current
    val state by viewModel.uiState.collectAsState()
    val total = state.totalPrice

    LaunchedEffect(key1 = true) {
        launch {
            viewModel.effect.collect { effect ->
                when(effect) {
                    is CartEffect.ShowToast -> Toast.makeText(context, effect.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Keranjang") },
                navigationIcon = {
                    IconButton({ navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "icon navigate pop")
                    }
                }
            )
        },
        content = { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                verticalArrangement = Arrangement.Center
            ) {
                Box(Modifier.weight(1f)) {
                    ContainerListCart(viewModel, state)
                }
                ContainerButtonAndTotal(total)
            }
        }
    )
}

@Composable
private fun ContainerListCart(viewModel: CartViewModel, uiState: CartState) {
    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp),
        contentPadding = PaddingValues(vertical = 20.dp)
    ) {
        when(uiState.isCartLoading) {
            true -> items(10) {
                ItemCartTile(true)
            }
            else -> items(uiState.products.size) { index ->
                val item = uiState.products[index]
                ItemCartTile(false, item = item) { id, qty ->
                    viewModel.onIntent(CartIntent.UpdateQty(id, qty))
                }
            }
        }
    }
}

@Composable
private fun ItemCartTile(isLoading: Boolean, item: CartProduk? = null, onValueChanged: ((Int, Int) -> Unit)? = null) {
    SwipeToRevealCard(
        onDeleteClick = { }
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(125.dp)
        ) {
            ListItem(
                modifier = Modifier.fillMaxWidth()
                    .background(MaterialTheme.colorScheme.surface, RoundedCornerShape(12.dp)),
                shadowElevation = 8.dp,
                tonalElevation = 16.dp,
                leadingContent = {
                    when (isLoading) {
                        true -> SkeletonPlaceholder(
                            modifier = Modifier.size(100.dp)
                        )
                        else -> AsyncImage(
                            model = item!!.imageUrl,
                            contentDescription = "image produk",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier.size(100.dp)
                        )
                    }
                },
                headlineContent = {
                    Column(
                        horizontalAlignment = Alignment.Start,
                        verticalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        when (isLoading) {
                            true -> {
                                SkeletonPlaceholder(Modifier.width(56.dp))
                                SkeletonPlaceholder(Modifier.width(72.dp))
                                SkeletonPlaceholder(Modifier.fillMaxWidth())
                            }
                            else -> {
                                Text(
                                    item!!.name,
                                    style = MaterialTheme.typography.titleLarge,
                                    fontWeight = FontWeight.Medium
                                )
                                Text(
                                    item.totalPrice.toRupiah(),
                                    style = MaterialTheme.typography.bodyLarge,
                                    color = MaterialTheme.colorScheme.tertiary
                                )
                                Text(
                                    item.deskripsi,
                                    overflow = TextOverflow.Ellipsis,
                                    fontSize = 12.sp,
                                    lineHeight = 1.5.em
                                )
                            }
                        }
                    }
                },
                trailingContent = {
                    NumberStepper(
                        modifier = Modifier
                            .fillMaxHeight()
                            .padding(vertical = 8.dp),
                        quantity = if (isLoading) 0 else item!!.quantity,
                        onValueChanged = {
                            if (onValueChanged != null) {
                                onValueChanged(item!!.productId, it)
                            }
                        }
                    )
                }
            )
        }
    }
}

@Composable
private fun ContainerButtonAndTotal(total: Double) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(IntrinsicSize.Min)
            .padding(20.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text("Total:")
        Box(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.primary, RoundedCornerShape(12.dp))
                .padding(horizontal = 16.dp, vertical = 8.dp),
        ) {
            Text(
                total.toRupiah(),
                color = Color.White
            )
        }
    }
}