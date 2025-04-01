package com.example.petuk.viewmodel

import android.content.Context
import android.content.SharedPreferences
import com.example.petuk.repository.StoreItem
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class StoreManager(context: Context) {
    private val sharedPreferences: SharedPreferences = context.getSharedPreferences("store_prefs", Context.MODE_PRIVATE)
    private val gson = Gson()
    private val storeListType = object : TypeToken<List<StoreItem>>() {}.type
    private val maxStores = 10 // Maximum number of recent stores to keep

    fun saveStore(storeId: String) {
        val currentStores = getRecentStores().toMutableList()

        // Check if store already exists, remove it to add it at the top
        currentStores.removeIf { it.storeId == storeId }

        // Add the new store at the beginning
        currentStores.add(0, StoreItem(storeId))

        // Keep only the most recent stores
        val updatedStores = if (currentStores.size > maxStores) {
            currentStores.subList(0, maxStores)
        } else {
            currentStores
        }

        // Save to SharedPreferences
        val storesJson = gson.toJson(updatedStores)
        sharedPreferences.edit().putString("recent_stores", storesJson).apply()
    }

    fun getRecentStores(): List<StoreItem> {
        val storesJson = sharedPreferences.getString("recent_stores", null) ?: return emptyList()
        return try {
            gson.fromJson(storesJson, storeListType)
        } catch (e: Exception) {
            emptyList()
        }
    }

    fun clearStores() {
        sharedPreferences.edit().remove("recent_stores").apply()
    }
}