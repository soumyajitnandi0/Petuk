package com.example.petuk

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class SuccessfulOrdersFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_successful_orders, container, false)

        val recyclerView = view.findViewById<RecyclerView>(R.id.recycler_successful_orders)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        // Dummy data (replace with actual data fetching from database)
        val successfulOrders = listOf(
            OrderModel("Order #123", "₹250", "Completed", "12 March 2025"),
            OrderModel("Order #456", "₹180", "Completed", "10 March 2025"),
            OrderModel("Order #789", "₹500", "Completed", "08 March 2025")
        )

        recyclerView.adapter = OrderAdapter(successfulOrders)

        return view
    }

}