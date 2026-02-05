package io.alramdhan.lanadi.data.remote.dto.response

import com.google.gson.annotations.SerializedName
import io.alramdhan.lanadi.domain.models.MetodePembayaran
import io.alramdhan.lanadi.domain.models.StatusPembayaran

data class OrderResponse(
    val id: Int,
    @SerializedName("trx_invoice")
    val trxInvoice: String,
    @SerializedName("user_id")
    val userId: Int,
    @SerializedName("total_belanja")
    val totalBelanja: Float,
    @SerializedName("pay_amount")
    val payAmount: Float,
    @SerializedName("change_amount")
    val changeAmount: Float,
    @SerializedName("status_pembayaran")
    val statusPembayaran: StatusPembayaran,
    @SerializedName("metode_pembayaran")
    val metodePembayaraan: MetodePembayaran,
    @SerializedName("created_at")
    val createdAt: String,
    val items: List<Item>
)

data class Item(
    val id: Int,
    @SerializedName("transaction_id")
    val transactionId: Int,
    @SerializedName("produk_id")
    val produkId: Int,
    val quantity: Int,
    val price: String,
    val subtotal: String,
    val produk: GetProdukResponse
)
