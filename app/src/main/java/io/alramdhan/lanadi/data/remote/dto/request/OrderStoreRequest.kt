package io.alramdhan.lanadi.data.remote.dto.request

import com.google.gson.annotations.SerializedName
import io.alramdhan.lanadi.domain.models.CartProduk

data class OrderStoreRequest(
    val items: List<CartProduk>,
    @SerializedName("pay_amount")
    val payAmount: Double,
    @SerializedName("payment_method")
    val paymentMethod: String = "cash",
    @SerializedName("nama_pelanggan")
    val namaPelanggan: String
)
