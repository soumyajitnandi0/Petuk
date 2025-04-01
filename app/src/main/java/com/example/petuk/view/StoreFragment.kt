package com.example.petuk.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.content.Intent
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.petuk.R
import com.example.petuk.viewmodel.StoreManager

class StoreFragment : Fragment() {
    private lateinit var storeManager: StoreManager
    private lateinit var recentStoresAdapter: RecentStoresAdapter
    private lateinit var emptyStateView: LinearLayout
    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAllText: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_store, container, false)

        // Initialize StoreManager
        storeManager = StoreManager(requireContext())

        // Initialize UI elements
        val storeIdInput = view.findViewById<EditText>(R.id.et_store_id)
        val enterButton = view.findViewById<Button>(R.id.btn_enter_store)
        recyclerView = view.findViewById(R.id.recycler_rewards_history)
        emptyStateView = view.findViewById(R.id.empty_state)

        // Correctly access the "View All" TextView
        val recentStoresLayout = view.findViewById<LinearLayout>(R.id.tv_recent_stores)
        viewAllText = recentStoresLayout.getChildAt(1) as TextView

        // Setup RecyclerView
        setupRecyclerView()

        // Load recent stores
        loadRecentStores()

        // Setup click listeners
        enterButton.setOnClickListener {
            val storeId = storeIdInput.text.toString().trim()
            if (storeId.isNotEmpty()) {
                // Save store ID
                storeManager.saveStore(storeId)

                // Refresh the list
                loadRecentStores()

                Toast.makeText(requireContext(), "Fetching store details for ID: $storeId", Toast.LENGTH_SHORT).show()

                // Create intent to launch EnterStore activity
                val intent = Intent(requireContext(), EnterStore::class.java)
                intent.putExtra("STORE_ID", storeId)
                startActivity(intent)

                // Clear the input field
                storeIdInput.text.clear()
            } else {
                Toast.makeText(requireContext(), "Enter a valid STORE ID", Toast.LENGTH_SHORT).show()
            }
        }

        // View All click listener
        viewAllText.setOnClickListener {
            // You can implement a full screen activity to show all stores
            Toast.makeText(requireContext(), "View all stores clicked", Toast.LENGTH_SHORT).show()
        }

        return view
    }

    private fun setupRecyclerView() {
        recentStoresAdapter = RecentStoresAdapter(requireContext())
        recyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = recentStoresAdapter
        }
    }

    private fun loadRecentStores() {
        val recentStores = storeManager.getRecentStores()

        // Update empty state visibility
        if (recentStores.isEmpty()) {
            emptyStateView.visibility = View.VISIBLE
            recyclerView.visibility = View.GONE
        } else {
            emptyStateView.visibility = View.GONE
            recyclerView.visibility = View.VISIBLE

            // Update the adapter
            recentStoresAdapter.updateStores(recentStores)
        }
    }

    override fun onResume() {
        super.onResume()
        // Refresh the list when returning to this fragment
        loadRecentStores()
    }
}