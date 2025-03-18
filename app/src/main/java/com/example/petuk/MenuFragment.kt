package com.example.petuk

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.petuk.databinding.FragmentMenuBinding
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton

class MenuFragment : Fragment() {

    private var storeId: String = ""
    private lateinit var binding: FragmentMenuBinding
    private lateinit var menuAdapter: MenuAdapter

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
        binding = FragmentMenuBinding.inflate(inflater, container, false)
        val view = binding.root

        // Setup RecyclerView for menu items
        val recyclerView = view.findViewById<RecyclerView>(R.id.recycler_menu)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        // Sample data - in a real app, this would come from API or database
        val menuItems = listOf(
            // Burgers
            MenuItem("1", "Veg Burger", "Classic veg burger with cheese and fresh veggies", 99.0, true),
            MenuItem("2", "Chicken Burger", "Juicy chicken patty with special sauce", 149.0, false),
            MenuItem("3", "Double Cheese Burger", "Extra cheese with double patty", 159.0, true),
            MenuItem("4", "Spicy Chicken Burger", "Hot and spicy chicken with jalapeños", 169.0, false),

            // Sides
            MenuItem("5", "French Fries", "Crispy golden fries with seasoning", 79.0, true),
            MenuItem("6", "Chicken Wings", "Spicy wings with dipping sauce", 159.0, false),
            MenuItem("7", "Onion Rings", "Crispy battered onion rings", 89.0, true),
            MenuItem("8", "Paneer Fingers", "Crunchy paneer sticks with mint dip", 129.0, true),

            // Beverages
            MenuItem("9", "Cold Coffee", "Refreshing cold coffee with ice cream", 129.0, true),
            MenuItem("10", "Chocolate Shake", "Rich chocolate shake with whipped cream", 109.0, true),
            MenuItem("11", "Strawberry Smoothie", "Fresh strawberry smoothie with ice", 119.0, true),
            MenuItem("12", "Lemonade", "Refreshing lemonade with mint", 69.0, true),

            // Desserts
            MenuItem("13", "Chocolate Brownie", "Warm chocolate brownie with ice cream", 149.0, true),
            MenuItem("14", "Cheesecake", "Creamy New York style cheesecake", 169.0, true),
            MenuItem("15", "Ice Cream Sundae", "Vanilla ice cream with chocolate sauce", 99.0, true)
        )

        // Set the adapter and connect it to the recyclerView
        menuAdapter = MenuAdapter(menuItems) { totalItems ->
            // Update cart button
            updateCartButton(totalItems)
        }
        recyclerView.adapter = menuAdapter

        // Show empty state if menu is empty
        val emptyView = view.findViewById<LinearLayout>(R.id.empty_menu_view)
        if (menuItems.isEmpty()) {
            recyclerView.visibility = View.GONE
            emptyView.visibility = View.VISIBLE
        } else {
            recyclerView.visibility = View.VISIBLE
            emptyView.visibility = View.GONE
        }

        // Set up click listener for the cart button
        val fabCart = view.findViewById<ExtendedFloatingActionButton>(R.id.fab_cart)
        fabCart.setOnClickListener {
            navigateToCartActivity()
        }

        return view
    }

    private fun updateCartButton(itemCount: Int) {
        val fabCart = view?.findViewById<ExtendedFloatingActionButton>(R.id.fab_cart)
        fabCart?.text = "Cart ($itemCount)"
    }

    private fun navigateToCartActivity() {
        // Create an Intent to navigate to CartActivity
        val intent = Intent(requireContext(), CartActivity::class.java).apply {
            // Pass the cart items to CartActivity
            putParcelableArrayListExtra("CART_ITEMS", ArrayList(menuAdapter.getCartItems()))
        }
        startActivity(intent)
    }
}