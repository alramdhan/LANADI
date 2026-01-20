package io.alramdhan.lanadi.core.utils

import java.text.NumberFormat
import java.util.Locale

fun Number.toRupiah(): String {
    val localeID = Locale("in", "ID")
    val numberFormat = NumberFormat.getCurrencyInstance(localeID)

    numberFormat.maximumFractionDigits = 0

    return numberFormat.format(this)
}