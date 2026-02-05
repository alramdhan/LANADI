package io.alramdhan.lanadi.domain.models

enum class StatusPembayaran(var label: String) {
    PAID("SUDAH DIBAYAR"),
    PENDING("Menunggu Pembayaran"),
    FAILED("Gagal"),
    CANCELED("Dibatalkan"),
    REFUNDED("Dikembalikan")
}