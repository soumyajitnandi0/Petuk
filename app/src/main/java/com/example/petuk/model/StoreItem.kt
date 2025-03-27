package com.example.petuk.model

data class StoreItem(
    val storeId: String,
    val timestamp: Long = System.currentTimeMillis()
)
