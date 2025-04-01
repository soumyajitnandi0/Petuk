package com.example.petuk.repository

data class Coupon(
    val code: String,
    val description: String,
    val type: CouponType,
    val value: Double
)
