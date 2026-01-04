package io.alramdhan.lanadi.core.di

import io.alramdhan.lanadi.data.repository.AuthRepositoryImpl
import io.alramdhan.lanadi.domain.repository.IAuthRepository
import io.alramdhan.lanadi.domain.usecase.LoginUseCase
import io.alramdhan.lanadi.viewmodels.auth.LoginViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    single<IAuthRepository> {
        AuthRepositoryImpl(get())
    }
    factory { LoginUseCase(get()) }
    viewModel { LoginViewModel(get()) }
}