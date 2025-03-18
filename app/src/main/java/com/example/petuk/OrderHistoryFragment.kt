package com.example.petuk

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class OrderHistoryFragment : Fragment() {

    private var storeId: String = ""

    companion object {
        fun newInstance(storeId: String): OrderHistoryFragment {
            val fragment = OrderHistoryFragment()
            val args = Bundle()
            args.putString("STORE_ID", storeId)
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            storeId = it.getString("STORE_ID") ?: ""
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_order_history, container, false)

        // Setup RecyclerView for order history
        val recyclerView = view.findViewById<RecyclerView>(R.id.recycler_order_history)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        // Sample data - in a real app, this would come from API or database
        val orderItems = listOf(
            OrderItem("ORD123", "2025-03-15", 350.0, "Delivered", "Veg Burger, Fries, Cold Coffee"),
            OrderItem("ORD119", "2025-03-10", 250.0, "Delivered", "Chicken Burger, Coke"),
            OrderItem("ORD112", "2025-03-05", 199.0, "Delivered", "French Fries, Cold Coffee")
        )

        // Set the adapter (we need to create this)
        // recyclerView.adapter = OrderHistoryAdapter(orderItems)

        return view
    }
}

// Data class for order history items
data class OrderItem(
    val orderId: String,
    val date: String,
    val amount: Double,
    val status: String,
    val items: String
)