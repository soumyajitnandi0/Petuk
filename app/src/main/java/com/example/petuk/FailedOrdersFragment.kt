package com.example.petuk

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class FailedOrdersFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_failed_orders, container, false)

        val recyclerView = view.findViewById<RecyclerView>(R.id.recycler_failed_orders)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        // Dummy failed orders data (replace with actual data fetching)
        val failedOrders = listOf(
            OrderModel("Order #321", "₹200", "Canceled", "11 March 2025"),
            OrderModel("Order #654", "₹150", "Failed", "07 March 2025")
        )

        recyclerView.adapter = OrderAdapter(failedOrders)

        return view
    }

}