package com.example.petuk

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.button.MaterialButton

class OrderHistoryFragment : Fragment() {

    private var storeId: String = ""
    private lateinit var recyclerView: RecyclerView
    private lateinit var emptyView: View
    private lateinit var orderHistoryAdapter: OrderHistoryAdapter
    private lateinit var orderRepository: OrderRepository
    private lateinit var rewardRepository: RewardRepository

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
        // Initialize repositories in onCreate
        orderRepository = OrderRepository(requireContext())
        rewardRepository = RewardRepository(requireContext())
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

        // Set up "Browse Menu" button in empty view
        view.findViewById<MaterialButton>(R.id.btn_go_to_menu)?.setOnClickListener {
            // Navigate back to menu
            requireActivity().supportFragmentManager.popBackStack()
        }

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
            val totalRewards = totalSpent * 0.10 // Calculate rewards as 10% of total spent

            view?.findViewById<TextView>(R.id.tv_total_orders)?.text = orders.size.toString()
            view?.findViewById<TextView>(R.id.tv_total_spent)?.text = "₹${totalSpent.toInt()}"

            // Update rewards display in the stats card
            view?.findViewById<TextView>(R.id.tv_rewards_earned)?.text = "₹${totalRewards.toInt()}"
        }
    }
}