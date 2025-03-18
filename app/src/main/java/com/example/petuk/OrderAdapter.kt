package com.example.petuk

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class OrderAdapter(private val orderList: List<OrderModel>) : RecyclerView.Adapter<OrderAdapter.OrderViewHolder>() {

    class OrderViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val orderId: TextView = view.findViewById(R.id.tv_order_id)
        val amount: TextView = view.findViewById(R.id.tv_amount)
        val status: TextView = view.findViewById(R.id.tv_status)
        val date: TextView = view.findViewById(R.id.tv_date)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_order, parent, false)
        return OrderViewHolder(view)
    }

    override fun onBindViewHolder(holder: OrderViewHolder, position: Int) {
        val order = orderList[position]
        holder.orderId.text = order.orderId
        holder.amount.text = order.amount
        holder.status.text = order.status
        holder.date.text = order.date
    }

    override fun getItemCount(): Int = orderList.size
}
