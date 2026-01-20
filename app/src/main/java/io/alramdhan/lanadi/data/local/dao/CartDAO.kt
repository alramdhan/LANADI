package io.alramdhan.lanadi.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import io.alramdhan.lanadi.data.local.entity.CartEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CartDAO {
    @Query("SELECT * FROM cart")
    fun getCarts(): Flow<List<CartEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(item: CartEntity)
}