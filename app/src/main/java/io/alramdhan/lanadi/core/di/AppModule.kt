package io.alramdhan.lanadi.core.di

import io.alramdhan.lanadi.data.repository.AuthRepositoryImpl
import io.alramdhan.lanadi.data.repository.ProdukRepositoryImpl
import io.alramdhan.lanadi.domain.repository.IAuthRepository
import io.alramdhan.lanadi.domain.repository.IProdukRepository
import io.alramdhan.lanadi.domain.usecase.GetKategoriUseCase
import io.alramdhan.lanadi.domain.usecase.LoginUseCase
import io.alramdhan.lanadi.domain.usecase.LogoutUseCase
import io.alramdhan.lanadi.viewmodels.auth.LoginViewModel
import io.alramdhan.lanadi.viewmodels.home.HomeViewModel
import io.alramdhan.lanadi.viewmodels.home.setting.SettingViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
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
    viewModel { HomeViewModel(get()) }
}