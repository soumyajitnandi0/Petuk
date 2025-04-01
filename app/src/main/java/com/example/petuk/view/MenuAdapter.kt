package com.example.petuk.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.petuk.repository.CartItem
import com.example.petuk.R
import com.example.petuk.repository.MenuItem

class MenuAdapter(
    private val menuItems: List<MenuItem>,
    private val onCartUpdate: (Int) -> Unit // Lambda to notify cart updates
) : RecyclerView.Adapter<MenuAdapter.MenuViewHolder>() {

    // Cart items counter
    private val cartItems = mutableMapOf<String, Int>()

    class MenuViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val vegIndicator: ImageView = view.findViewById(R.id.img_veg_indicator)
        val itemName: TextView = view.findViewById(R.id.tv_item_name)
        val itemDescription: TextView = view.findViewById(R.id.tv_item_description)
        val itemPrice: TextView = view.findViewById(R.id.tv_item_price)
        val addButton: Button = view.findViewById(R.id.btn_add)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MenuViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_menu, parent, false)
        return MenuViewHolder(view)
    }

    override fun onBindViewHolder(holder: MenuViewHolder, position: Int) {
        val item = menuItems[position]

        // Set veg/non-veg indicator
        holder.vegIndicator.setImageResource(
            if (item.isVeg) R.drawable.baseline_crop_square_24 // Green square for veg
            else R.drawable.baseline_crop_din_24 // Red square for non-veg
        )

        // Set indicator color based on veg status
        holder.vegIndicator.setColorFilter(
            holder.itemView.context.getColor(
                if (item.isVeg) R.color.veg_green
                else R.color.non_veg_red
            )
        )

        // Bind data to views
        holder.itemName.text = item.name
        holder.itemDescription.text = item.description
        holder.itemPrice.text = "₹${item.price.toInt()}"

        // Add to cart button click listener
        holder.addButton.setOnClickListener {
            // Add to cart
            val count = cartItems.getOrDefault(item.id, 0) + 1
            cartItems[item.id] = count

            // Notify cart update
            onCartUpdate(cartItems.values.sum())

            // Show success message
            Toast.makeText(
                holder.itemView.context,
                "${item.name} added to cart",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    override fun getItemCount() = menuItems.size

    // Function to get cart items
    fun getCartItems(): List<CartItem> {
        val result = mutableListOf<CartItem>()

        for ((id, quantity) in cartItems) {
            // Find the corresponding menu item
            menuItems.find { it.id == id }?.let { menuItem ->
                result.add(
                    CartItem(
                        menuItem.id,
                        menuItem.name,
                        menuItem.price,
                        menuItem.isVeg,
                        true, // Set isAvailable to true
                        quantity
                    )
                )
            }
        }

        return result
    }
}