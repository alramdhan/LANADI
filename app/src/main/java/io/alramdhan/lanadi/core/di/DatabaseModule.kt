package io.alramdhan.lanadi.core.di

import androidx.room.Room
import io.alramdhan.lanadi.data.local.database.AppDatabase
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