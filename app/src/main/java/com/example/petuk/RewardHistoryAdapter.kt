package com.example.petuk

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class RewardHistoryAdapter(private val rewards: List<RewardTransaction>) :
    RecyclerView.Adapter<RewardHistoryAdapter.RewardViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RewardViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_reward_history, parent, false)
        return RewardViewHolder(view)
    }

    override fun onBindViewHolder(holder: RewardViewHolder, position: Int) {
        val reward = rewards[position]
        holder.bind(reward)
    }

    override fun getItemCount(): Int {
        return rewards.size
    }

    class RewardViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvRewardAmount: TextView = itemView.findViewById(R.id.tv_reward_amount)
        private val tvOrderInfo: TextView = itemView.findViewById(R.id.tv_order_info)
        private val tvRewardDate: TextView = itemView.findViewById(R.id.tv_reward_date)

        fun bind(reward: RewardTransaction) {
            tvRewardAmount.text = "+₹${reward.amount.toInt()}"
            tvOrderInfo.text = "Order #${reward.orderId} at ${reward.storeId}"
            tvRewardDate.text = reward.date
        }
    }
}