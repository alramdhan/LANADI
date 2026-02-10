package io.alramdhan.lanadi.viewmodels.home.cart

import androidx.compose.runtime.Composable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import io.alramdhan.lanadi.core.ui.dialog.DialogManager
import io.alramdhan.lanadi.core.ui.sheet.SheetManager
import io.alramdhan.lanadi.domain.models.CartProduk
import io.alramdhan.lanadi.domain.usecase.AddToCartUseCase
import io.alramdhan.lanadi.domain.usecase.DeleteAllCartsUseCase
import io.alramdhan.lanadi.domain.usecase.DeleteCartUseCase
import io.alramdhan.lanadi.domain.usecase.GetCartUseCase
import io.alramdhan.lanadi.domain.usecase.UpdateCartQtyUseCase
import io.alramdhan.lanadi.ui.home.cart.CartEffect
import io.alramdhan.lanadi.ui.home.cart.CartIntent
import io.alramdhan.lanadi.ui.home.cart.CartState
import io.alramdhan.lanadi.ui.animations.FlyingItem
import io.alramdhan.lanadi.ui.home.checkout.CheckoutScreen
import io.alramdhan.lanadi.viewmodels.home.checkout.CheckoutViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CartViewModel(
    private val getCart: GetCartUseCase,
    private val addToCart: AddToCartUseCase,
    private val updateCartQty: UpdateCartQtyUseCase,
    private val deleteCart: DeleteCartUseCase,
    private val deleteAllCarts: DeleteAllCartsUseCase,
    private val dialogManager: DialogManager,
    private val sheetManager: SheetManager
) : ViewModel() {
    private val _uiState = MutableStateFlow(CartState())
    val uiState = _uiState.asStateFlow()

    private val _effect = Channel<CartEffect>()
    val effect = _effect.receiveAsFlow()

    init {
        onIntent(CartIntent.LoadCart)
    }

    fun onIntent(intent: CartIntent) {
        when(intent) {
            is CartIntent.UpdateCartPosition -> _uiState.update { it.copy(cartIconPosition = intent.position) }
            is CartIntent.LoadCart -> fetchCart()
            is CartIntent.AddItem -> {
                val newItem = FlyingItem(
                    product = intent.item,
                    image = intent.painter,
                    startPosition = intent.startPosition,
                    startSize = intent.startSize,
                    targetPosition = _uiState.value.cartIconPosition
                )
                _uiState.update {
                    it.copy(flyingItems = it.flyingItems + newItem)
                }
                addItem(intent.item)
            }
            is CartIntent.UpdateQty -> updateQty(intent.productId, intent.qty)
            is CartIntent.DeleteItem -> deleteItem(intent.item)
            is CartIntent.DeleteAllItems -> deleteAllItems()
            is CartIntent.OnChangeNamaPelanggan -> _uiState.update { it.copy(namaPelanggan = intent.nama) }
            is CartIntent.CheckoutClicked -> navigateToCheckout()
            is CartIntent.OpenModalCheckout -> openBottomSheetCO(intent.screen)
            is CartIntent.AnimationFinished -> {
                _uiState.update { it.copy(flyingItems = it.flyingItems.filter { fi -> fi.id != intent.flyingItemsId }) }
            }
        }
    }

    fun onDeleteItemClicked() {
        dialogManager.show(
            "Perhatian",
            message = "Apakah yakin ingin menghapus semua pesanan?",
            showConfirmButton = true,
            onConfirm = { onIntent(CartIntent.DeleteAllItems) }
        )
    }

    private fun fetchCart() {
        viewModelScope.launch {
            _uiState.update { it.copy(isCartLoading = true) }
            getCart().collect { result ->
                _uiState.update { it.copy(isCartLoading = false, products = result) }
            }
        }
    }

    private fun addItem(item: CartProduk) {
        viewModelScope.launch {
            delay(1000)
            when(checkIfProdukHasAdded(item)) {
                true -> {
                    updateCartQty(item.productId, 1)
                    val qtyP = _uiState.value.products.first { it.productId == item.productId }
                    _effect.send(CartEffect.ShowToast("${qtyP.quantity + 1} ${item.name}"))
                }
                else -> {
                    addToCart(item)
                    _effect.send(CartEffect.ShowToast("Berhasil menambahkan ke keranjang"))
                }
            }
        }
    }

    private fun updateQty(id: Int, qty: Int) {
        viewModelScope.launch {
            updateCartQty(id, qty)
        }
    }

    private fun deleteItem(item: CartProduk) {
        viewModelScope.launch {
            deleteCart(item)
            _effect.send(CartEffect.ShowToast("Produk berhasil dihapus"))
        }
    }

    private fun deleteAllItems() {
        viewModelScope.launch {
            deleteAllCarts()
            _effect.send(CartEffect.ShowToast("Semua item dihapus"))
        }
    }

    private fun navigateToCheckout() {
        if(_uiState.value.products.isEmpty()) {
            dialogManager.show(
                title = "Keranjang Kosong",
                message = "Belum ada produk yang ditambahkan ke dalam pesanan ini.",
            )
        } else if(_uiState.value.namaPelanggan.isEmpty()) {
            _uiState.update { it.copy(errorNamaPelanggan = "Nama Pelanggan harus diisi") }
        } else {
            _uiState.update { it.copy(errorNamaPelanggan = null, checkoutProcess = true) }
            viewModelScope.launch {
                delay(200)
                _uiState.update { it.copy(checkoutProcess = false) }
                _effect.send(CartEffect.NavigateToCheckout)
            }
        }
    }

    private fun openBottomSheetCO(checkoutScreen: @Composable () -> Unit) {
        sheetManager.showSheet({},
            content = checkoutScreen
        )
    }

    private fun checkIfProdukHasAdded(item: CartProduk): Boolean {
        val exist = _uiState.value.products.filter { item.productId == it.productId }
        return exist.isNotEmpty()
    }
}