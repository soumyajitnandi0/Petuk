package com.example.petuk

data class Coupon(
    val code: String,
    val description: String,
    val type: CouponType,
    val value: Double
)
