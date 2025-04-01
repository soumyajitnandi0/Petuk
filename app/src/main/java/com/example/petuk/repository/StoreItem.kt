package com.example.petuk.repository

data class StoreItem(
    val storeId: String,
    val timestamp: Long = System.currentTimeMillis()
)
