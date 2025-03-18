package com.example.petuk

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView

class OrderHistoryAdapter(private val orderItems: List<OrderItem>) :
    RecyclerView.Adapter<OrderHistoryAdapter.OrderViewHolder>() {

    class OrderViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val orderId: TextView = view.findViewById(R.id.tv_order_id)
        val status: TextView = view.findViewById(R.id.tv_status)
        val amount: TextView = view.findViewById(R.id.tv_amount)
        val date: TextView = view.findViewById(R.id.tv_date)
        val viewDetailsButton: Button = view.findViewById(R.id.btn_view_details)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_order, parent, false)
        return OrderViewHolder(view)
    }

    override fun onBindViewHolder(holder: OrderViewHolder, position: Int) {
        val order = orderItems[position]

        holder.orderId.text = "Order #${order.orderId}"
        holder.status.text = order.status
        holder.amount.text = "Amount: ₹${order.amount.toInt()}"
        holder.date.text = "Date: ${order.date}"

        holder.viewDetailsButton.setOnClickListener {
            // Implement view details functionality
            Toast.makeText(
                holder.itemView.context,
                "Viewing details for order ${order.orderId}",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    override fun getItemCount() = orderItems.size
}