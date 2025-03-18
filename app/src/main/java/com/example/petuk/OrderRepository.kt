package com.example.petuk

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.text.SimpleDateFormat
import java.util.*

class OrderRepository(context: Context) {
    private val sharedPreferences: SharedPreferences = context.getSharedPreferences(
        "orders_prefs", Context.MODE_PRIVATE
    )
    private val gson = Gson()

    fun saveOrder(items: List<CartItem>, total: Double, couponApplied: String?): String {
        // Get current orders
        val orders = getAllOrders().toMutableList()

        // Generate order ID (simple format: ORD + random numbers)
        val orderId = "ORD" + (100 + Random().nextInt(900)).toString()

        // Get current date
        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val currentDate = dateFormat.format(Date())

        // Create item description
        val itemsDescription = items.joinToString(", ") { "${it.name} x${it.quantity}" }

        // Create new order
        val newOrder = OrderItem(
            orderId = orderId,
            date = currentDate,
            amount = total,
            status = "Processing",
            items = itemsDescription
        )

        // Add to list
        orders.add(0, newOrder) // Add at beginning (most recent first)

        // Save updated list
        val ordersJson = gson.toJson(orders)
        sharedPreferences.edit().putString("orders", ordersJson).apply()

        return orderId
    }

    fun getAllOrders(): List<OrderItem> {
        // Retrieve the saved orders JSON from SharedPreferences
        val ordersJson = sharedPreferences.getString("orders", null)

        // If no orders are saved, return an empty list
        if (ordersJson.isNullOrEmpty()) {
            return emptyList()
        }

        // Parse the JSON into a list of OrderItem objects
        val type = object : TypeToken<List<OrderItem>>() {}.type
        return gson.fromJson(ordersJson, type) ?: emptyList()
    }
}