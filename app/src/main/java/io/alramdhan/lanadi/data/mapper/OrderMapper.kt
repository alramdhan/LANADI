package io.alramdhan.lanadi.data.mapper

import io.alramdhan.lanadi.data.remote.dto.request.OrderStoreRequest
import io.alramdhan.lanadi.data.remote.dto.response.OrderResponse
import io.alramdhan.lanadi.domain.models.Order
import io.alramdhan.lanadi.domain.models.Transaction

fun OrderResponse.toDomain(): Transaction {
    return Transaction(
        id = this.id,
        trxInvoice = this.trxInvoice,
        userId = this.userId,
        totalBelanja = this.totalBelanja,
        payAmount = this.payAmount,
        changeAmount = this.changeAmount,
        statusPembayaran = this.statusPembayaran,
        metodePembayaraan = this.metodePembayaraan,
        createdAt = this.createdAt,
        items = this.items
    )
}

fun Order.toRemoteDTO(): OrderStoreRequest {
    return OrderStoreRequest(
        items = this.items,
        payAmount = this.payAmount,
        namaPelanggan = this.namaPelanggan,
        paymentMethod = this.paymentMethod
    )
}