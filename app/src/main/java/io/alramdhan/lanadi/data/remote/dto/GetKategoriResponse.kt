package io.alramdhan.lanadi.data.remote.dto

import com.google.gson.annotations.SerializedName

data class GetKategoriResponse(
    val kategori: List<ItemKategori>
)

data class ItemKategori(
    val id: Int,
    val name: String,
    val image: String?,
    @SerializedName("sort_order")
    val sortOrder: Int?
)
