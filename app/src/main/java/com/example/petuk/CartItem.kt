package com.example.petuk

import android.os.Parcel
import android.os.Parcelable

data class CartItem(
    val id: String,
    val name: String,
    val price: Double,
    val isVeg: Boolean,
    val isAvailable: Boolean,
    var quantity: Int
) : Parcelable {

    // Parcelable constructor
    constructor(parcel: Parcel) : this(
        parcel.readString() ?: "", // Read id
        parcel.readString() ?: "", // Read name
        parcel.readDouble(),       // Read price
        parcel.readByte() != 0.toByte(), // Read isVeg
        parcel.readByte() != 0.toByte(), // Read isAvailable
        parcel.readInt()          // Read quantity
    )

    // Write CartItem properties to a Parcel
    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeString(name)
        parcel.writeDouble(price)
        parcel.writeByte(if (isAvailable) 1 else 0)
        parcel.writeInt(quantity)
    }

    // Required method for Parcelable
    override fun describeContents(): Int {
        return 0
    }

    // Companion object to create CartItem instances from a Parcel
    companion object CREATOR : Parcelable.Creator<CartItem> {
        override fun createFromParcel(parcel: Parcel): CartItem {
            return CartItem(parcel)
        }

        override fun newArray(size: Int): Array<CartItem?> {
            return arrayOfNulls(size)
        }
    }
}