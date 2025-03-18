package com.example.petuk

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MenuFragment : Fragment() {

    private var storeId: String = ""

    companion object {
        fun newInstance(storeId: String): MenuFragment {
            val fragment = MenuFragment()
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
        val view = inflater.inflate(R.layout.fragment_menu, container, false)

        // Here you would typically fetch menu data from an API based on storeId
        // For demonstration, we'll just set up a sample menu

        // Setup RecyclerView for menu items (we'll need to create this adapter)
        val recyclerView = view.findViewById<RecyclerView>(R.id.recycler_menu)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        // Sample data - in a real app, this would come from API or database
        val menuItems = listOf(
            MenuItem("1", "Veg Burger", "Classic veg burger with cheese", 99.0, true),
            MenuItem("2", "Chicken Burger", "Juicy chicken patty with sauce", 149.0, false),
            MenuItem("3", "French Fries", "Crispy golden fries", 79.0, true),
            MenuItem("4", "Cold Coffee", "Refreshing cold coffee with ice cream", 129.0, true)
        )

        // Set the adapter (we need to create this)
        // recyclerView.adapter = MenuAdapter(menuItems)

        return view
    }
}

// Data class for menu items
data class MenuItem(
    val id: String,
    val name: String,
    val description: String,
    val price: Double,
    val isVeg: Boolean
)