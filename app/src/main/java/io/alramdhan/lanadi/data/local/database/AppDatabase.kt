package io.alramdhan.lanadi.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import io.alramdhan.lanadi.data.local.dao.CartDAO
import io.alramdhan.lanadi.data.local.entity.CartEntity

@Database(
    entities = [CartEntity::class],
    version = 1
)
abstract class AppDatabase: RoomDatabase() {
    abstract fun cartDao(): CartDAO
}