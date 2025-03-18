package com.example.petuk

import com.example.petuk.OrderHistoryAdapter
import com.example.petuk.OrderRepository
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class OrderHistoryFragment : Fragment() {

    private var storeId: String = ""
    private lateinit var recyclerView: RecyclerView
    private lateinit var emptyView: View
    private lateinit var orderHistoryAdapter: OrderHistoryAdapter
    private lateinit var orderRepository: OrderRepository

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

        // Initialize RecyclerView and empty view
        recyclerView = view.findViewById(R.id.recycler_order_history)
        emptyView = view.findViewById(R.id.empty_history_view)

        // Setup RecyclerView for order history
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        // Initialize adapter with empty list
        orderHistoryAdapter = OrderHistoryAdapter(emptyList())
        recyclerView.adapter = orderHistoryAdapter

        return view
    }

    override fun onResume() {
        super.onResume()
        loadOrders()
    }

    private fun loadOrders() {
        // Fetch orders from the repository
        val orders = orderRepository.getAllOrders()

        // Update UI based on whether there are orders
        if (orders.isEmpty()) {
            recyclerView.visibility = View.GONE
            emptyView.visibility = View.VISIBLE
        } else {
            recyclerView.visibility = View.VISIBLE
            emptyView.visibility = View.GONE

            // Update adapter with orders
            orderHistoryAdapter = OrderHistoryAdapter(orders)
            recyclerView.adapter = orderHistoryAdapter

            // Update stats
            val totalSpent = orders.sumOf { it.amount }
            view?.findViewById<TextView>(R.id.tv_total_orders)?.text = orders.size.toString()
            view?.findViewById<TextView>(R.id.tv_total_spent)?.text = "₹${totalSpent.toInt()}"
            // Can calculate rewards if needed
        }
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