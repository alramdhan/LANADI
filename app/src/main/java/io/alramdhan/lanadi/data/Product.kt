package io.alramdhan.lanadi.data

import io.alramdhan.lanadi.R

data class Product(
    val id: Int,
    val name: String,
    val price: String,
    val imageRes: Int
)

// Data Dummy
val dummyProducts = listOf(
    Product(1, "Produk A", "Rp 50.000", R.drawable.main_logo),
    Product(2, "Produk B", "Rp 75.000", R.drawable.main_logo),
    Product(3, "Produk C", "Rp 120.000", R.drawable.main_logo),
    Product(4, "Produk D", "Rp 30.000", R.drawable.main_logo),
    Product(5, "Produk E", "Rp 200.000", R.drawable.main_logo),
    Product(6, "Produk F", "Rp 45.000", R.drawable.main_logo),
)
