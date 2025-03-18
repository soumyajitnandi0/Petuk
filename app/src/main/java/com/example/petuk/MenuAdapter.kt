package com.example.petuk

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView

class MenuAdapter(private val menuItems: List<MenuItem>) :
    RecyclerView.Adapter<MenuAdapter.MenuViewHolder>() {

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
        // You would use the appropriate icons based on isVeg property
        holder.vegIndicator.setImageResource(
            if (item.isVeg) R.drawable.baseline_grid_3x3_24
            else R.drawable.baseline_grid_3x3_24
        )

        holder.itemName.text = item.name
        holder.itemDescription.text = item.description
        holder.itemPrice.text = "₹${item.price.toInt()}"

        holder.addButton.setOnClickListener {
            // Implement add to cart functionality
            Toast.makeText(
                holder.itemView.context,
                "${item.name} added to cart",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    override fun getItemCount() = menuItems.size
}