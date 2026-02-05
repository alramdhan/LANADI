package io.alramdhan.lanadi.domain.models

import io.alramdhan.lanadi.data.remote.dto.response.Item

data class Transaction(
    val id: Int,
    val trxInvoice: String,
    val userId: Int,
    val totalBelanja: Float,
    val payAmount: Float,
    val changeAmount: Float,
    val statusPembayaran: StatusPembayaran,
    val metodePembayaraan: MetodePembayaran,
    val createdAt: String,
    val items: List<Item>
)