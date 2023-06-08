package com.example.testcatering.ui.common

import android.location.Geocoder
import android.os.Build

fun Geocoder.getAddress(
    latitude: Double,
    longitude: Double,
    address: (android.location.Address?) -> Unit
) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        getFromLocation(latitude, longitude, 1) { address(it.firstOrNull()) }
        return
    }
    try {
        address(getFromLocation(latitude, longitude, 1)?.firstOrNull())
    } catch (e: Exception) {
        address(null)
    }
}