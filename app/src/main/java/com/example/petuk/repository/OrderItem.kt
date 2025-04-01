package com.example.petuk.repository

data class OrderItem(
    val orderId: String,
    val date: String,
    val amount: Double,
    val status: String,
    val items: String
)
