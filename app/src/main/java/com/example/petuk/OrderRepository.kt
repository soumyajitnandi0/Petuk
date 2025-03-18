package com.example.petuk

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.text.SimpleDateFormat
import java.util.*

class OrderRepository(private val context: Context) {

    private val PREF_NAME = "order_prefs"
    private val ORDERS_KEY = "orders"
    private val prefs: SharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
    private val gson = Gson()

    // Save a new order and return the generated order ID
    fun saveOrder(items: List<CartItem>, total: Double, couponApplied: String?): String {
        // Generate a random order ID
        val orderId = "ORD-" + UUID.randomUUID().toString().substring(0, 8)

        // Get current date and time
        val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault())
        val currentDate = dateFormat.format(Date())

        // Create item names string
        val itemNames = items.joinToString(", ") { "${it.name} x${it.quantity}" }

        // Create new order
        val order = OrderItem(
            orderId = orderId,
            date = currentDate,
            amount = total,
            status = "Processing",
            items = itemNames
        )

        // Get all existing orders
        val orders = getAllOrders().toMutableList()

        // Add new order
        orders.add(0, order) // Add to beginning of list (most recent first)

        // Save updated orders list
        saveAllOrders(orders)

        return orderId
    }

    // Get all orders
    fun getAllOrders(): List<OrderItem> {
        val ordersJson = prefs.getString(ORDERS_KEY, null) ?: return emptyList()

        val type = object : TypeToken<List<OrderItem>>() {}.type
        return gson.fromJson(ordersJson, type) ?: emptyList()
    }

    // Save all orders
    private fun saveAllOrders(orders: List<OrderItem>) {
        val ordersJson = gson.toJson(orders)
        prefs.edit().putString(ORDERS_KEY, ordersJson).apply()
    }
}