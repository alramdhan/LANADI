package io.alramdhan.lanadi.core.di

import io.alramdhan.lanadi.core.ui.dialog.DialogManager
import io.alramdhan.lanadi.core.ui.loading.LoadingManager
import io.alramdhan.lanadi.core.ui.sheet.SheetManager
import io.alramdhan.lanadi.core.ui.sheet.SheetManagerImpl
import io.alramdhan.lanadi.core.utils.DefaultDispatcherProvider
import io.alramdhan.lanadi.core.utils.DispatcherProvider
import io.alramdhan.lanadi.data.repository.AuthRepositoryImpl
import io.alramdhan.lanadi.data.repository.CartRepositoryImpl
import io.alramdhan.lanadi.data.repository.OrderRepositoryImpl
import io.alramdhan.lanadi.data.repository.ProdukRepositoryImpl
import io.alramdhan.lanadi.domain.repository.IAuthRepository
import io.alramdhan.lanadi.domain.repository.ICartRepository
import io.alramdhan.lanadi.domain.repository.IOrderRepository
import io.alramdhan.lanadi.domain.repository.IProdukRepository
import io.alramdhan.lanadi.domain.usecase.AddToCartUseCase
import io.alramdhan.lanadi.domain.usecase.DeleteAllCartsUseCase
import io.alramdhan.lanadi.domain.usecase.DeleteCartUseCase
import io.alramdhan.lanadi.domain.usecase.GetCartUseCase
import io.alramdhan.lanadi.domain.usecase.GetKategoriUseCase
import io.alramdhan.lanadi.domain.usecase.GetProdukUseCase
import io.alramdhan.lanadi.domain.usecase.LoginUseCase
import io.alramdhan.lanadi.domain.usecase.LogoutUseCase
import io.alramdhan.lanadi.domain.usecase.MakeAnOrderUseCase
import io.alramdhan.lanadi.domain.usecase.UpdateCartQtyUseCase
import io.alramdhan.lanadi.viewmodels.auth.LoginViewModel
import io.alramdhan.lanadi.viewmodels.home.HomeViewModel
import io.alramdhan.lanadi.viewmodels.home.cart.CartViewModel
import io.alramdhan.lanadi.viewmodels.home.checkout.CheckoutViewModel
import io.alramdhan.lanadi.viewmodels.home.setting.SettingViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val appModule = module {
    single<DispatcherProvider> { DefaultDispatcherProvider() }

    singleOf<DialogManager>(::DialogManager)
    singleOf<LoadingManager>(::LoadingManager)
    singleOf<SheetManager>(::SheetManagerImpl)

    single<IAuthRepository> {
        AuthRepositoryImpl(get(), get())
    }
    factory { LoginUseCase(get()) }
    viewModel { LoginViewModel(get()) }

    factory { LogoutUseCase(get()) }
    viewModel { SettingViewModel(get()) }

    single<IProdukRepository> {
        ProdukRepositoryImpl(get())
    }
    factory { GetKategoriUseCase(get()) }
    factory { GetProdukUseCase(get()) }
    viewModel { HomeViewModel(get(), get()) }

    single<ICartRepository> { CartRepositoryImpl(get(), get()) }

    factory { GetCartUseCase(get()) }
    factory { AddToCartUseCase(get()) }
    factory { UpdateCartQtyUseCase(get()) }
    factory { DeleteCartUseCase(get()) }
    factory { DeleteAllCartsUseCase(get()) }

    viewModel {
        CartViewModel(
            getCart = get(),
            addToCart =  get(),
            updateCartQty = get(),
            deleteCart = get(),
            deleteAllCarts = get(),
            dialogManager = get(),
            sheetManager = get()
        )
    }

    viewModelOf<CheckoutViewModel>(::CheckoutViewModel)

    single<IOrderRepository> { OrderRepositoryImpl(get()) }
    factory { MakeAnOrderUseCase(get()) }
}