package com.example.petuk.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.petuk.repository.OrderItem
import com.example.petuk.R

class OrderHistoryAdapter(private val orders: List<OrderItem>) :
    RecyclerView.Adapter<OrderHistoryAdapter.OrderViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_order_history, parent, false)
        return OrderViewHolder(view)
    }

    override fun onBindViewHolder(holder: OrderViewHolder, position: Int) {
        val order = orders[position]
        holder.bind(order)
    }

    override fun getItemCount(): Int {
        return orders.size
    }

    class OrderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvOrderId: TextView = itemView.findViewById(R.id.tv_order_id)
        private val tvOrderDate: TextView = itemView.findViewById(R.id.tv_order_date)
        private val tvOrderAmount: TextView = itemView.findViewById(R.id.tv_order_amount)
        private val tvOrderStatus: TextView = itemView.findViewById(R.id.tv_order_status)
        private val tvOrderItems: TextView = itemView.findViewById(R.id.tv_order_items)

        fun bind(order: OrderItem) {
            tvOrderId.text = "Order ID: ${order.orderId}"
            tvOrderDate.text = "Date: ${order.date}"
            tvOrderAmount.text = "Amount: ₹${order.amount}"
            tvOrderStatus.text = "Status: ${order.status}"
            tvOrderItems.text = "Items: ${order.items}"
        }
    }
}