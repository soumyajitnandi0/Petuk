package com.example.petuk

data class StoreItem(
    val storeId: String,
    val timestamp: Long = System.currentTimeMillis()
)
