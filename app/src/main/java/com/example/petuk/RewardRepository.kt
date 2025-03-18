package com.example.petuk

import android.content.Context
import android.content.SharedPreferences
import org.json.JSONArray
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class RewardRepository(private val context: Context) {

    private val sharedPreferences: SharedPreferences = context.getSharedPreferences(
        "reward_prefs", Context.MODE_PRIVATE
    )

    // Constants
    companion object {
        private const val KEY_TOTAL_REWARDS = "total_rewards"
        private const val KEY_REWARD_HISTORY = "reward_history"
        private const val REWARD_PERCENTAGE = 0.10 // 10% reward on orders
    }

    // Get total rewards accumulated
    fun getTotalRewards(): Double {
        return sharedPreferences.getFloat(KEY_TOTAL_REWARDS, 0f).toDouble()
    }

    // Add reward for a new order
    fun addReward(orderId: String, storeId: String, orderAmount: Double): Double {
        val rewardAmount = orderAmount * REWARD_PERCENTAGE

        // Update total rewards
        val currentRewards = getTotalRewards()
        val newTotalRewards = currentRewards + rewardAmount

        // Save updated total
        sharedPreferences.edit().putFloat(KEY_TOTAL_REWARDS, newTotalRewards.toFloat()).apply()

        // Add reward transaction to history
        addRewardToHistory(orderId, storeId, rewardAmount)

        return rewardAmount
    }

    // Add reward transaction to history
    private fun addRewardToHistory(orderId: String, storeId: String, rewardAmount: Double) {
        val dateFormat = SimpleDateFormat("dd MMM yyyy, HH:mm", Locale.getDefault())
        val currentDate = dateFormat.format(Date())

        // Create new reward history entry
        val rewardEntry = JSONObject().apply {
            put("orderId", orderId)
            put("storeId", storeId)
            put("amount", rewardAmount)
            put("date", currentDate)
        }

        // Get existing history
        val historyJson = sharedPreferences.getString(KEY_REWARD_HISTORY, "[]")
        val historyArray = JSONArray(historyJson)

        // Add new entry at the beginning
        val updatedArray = JSONArray()
        updatedArray.put(rewardEntry)

        // Add existing entries
        for (i in 0 until historyArray.length()) {
            updatedArray.put(historyArray.get(i))
        }

        // Save updated history
        sharedPreferences.edit().putString(KEY_REWARD_HISTORY, updatedArray.toString()).apply()
    }

    // Get reward history
    fun getRewardHistory(): List<RewardTransaction> {
        val historyJson = sharedPreferences.getString(KEY_REWARD_HISTORY, "[]")
        val historyArray = JSONArray(historyJson)
        val rewardList = mutableListOf<RewardTransaction>()

        for (i in 0 until historyArray.length()) {
            val entry = historyArray.getJSONObject(i)
            rewardList.add(
                RewardTransaction(
                    orderId = entry.getString("orderId"),
                    storeId = entry.getString("storeId"),
                    amount = entry.getDouble("amount"),
                    date = entry.getString("date")
                )
            )
        }

        return rewardList
    }
}

// Data class for reward transactions
data class RewardTransaction(
    val orderId: String,
    val storeId: String,
    val amount: Double,
    val date: String
)