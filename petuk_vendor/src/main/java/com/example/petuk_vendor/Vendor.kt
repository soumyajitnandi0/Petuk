package com.example.petuk_vendor

data class Vendor(
    val vendorId: String = "",
    val fullName: String = "",
    val email: String = "",
    val phone: String = "",
    val businessName: String = "",
    val businessAddress: String = "",
    val aadharNumber: String = "",
    val panNumber: String = "",
    val isVerified: Boolean = false,
    val referralCode: String = "",
    val createdAt: Long = 0
)
