package io.alramdhan.lanadi.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "carts")
data class CartEntity(
    @PrimaryKey(autoGenerate = true) val productId: Int,
    val name: String,
    val deskripsi: String,
    val price: Double,
    val quantity: Int,
    val imageUrl: String?
)