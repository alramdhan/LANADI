package io.alramdhan.lanadi.core.di

import androidx.room.Room
import io.alramdhan.lanadi.data.local.database.AppDatabase
import io.alramdhan.lanadi.data.repository.CartRepositoryImpl
import io.alramdhan.lanadi.domain.repository.ICartRepository
import io.alramdhan.lanadi.domain.usecase.AddToCartUseCase
import io.alramdhan.lanadi.domain.usecase.GetCartUseCase
import io.alramdhan.lanadi.viewmodels.home.cart.CartViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val databaseModule = module {
    single {
        Room.databaseBuilder(
            get(),
            AppDatabase::class.java,
            "pos_db"
        ).build()
    }

    single { get<AppDatabase>().cartDao() }
}