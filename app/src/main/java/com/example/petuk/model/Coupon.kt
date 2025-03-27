package com.example.petuk.model

data class Coupon(
    val code: String,
    val description: String,
    val type: CouponType,
    val value: Double
)
