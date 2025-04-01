package com.example.petuk.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.petuk.repository.CartItem
import com.example.petuk.R

class CartAdapter(
    private val cartItems: List<CartItem>,
    private val listener: OnCartItemListener
) : RecyclerView.Adapter<CartAdapter.CartViewHolder>() {

    interface OnCartItemListener {
        fun onCartItemRemove(position: Int)
        fun onCartItemQuantityChange(position: Int, quantity: Int)
    }

    class CartViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val vegIndicator: ImageView = view.findViewById(R.id.img_veg_indicator)
        val itemName: TextView = view.findViewById(R.id.tv_item_name)
        val itemPrice: TextView = view.findViewById(R.id.tv_item_price)
        val itemQuantity: TextView = view.findViewById(R.id.tv_quantity)
        val itemTotal: TextView = view.findViewById(R.id.tv_item_total)
        val btnRemove: ImageButton = view.findViewById(R.id.btn_remove)
        val btnDecrease: ImageButton = view.findViewById(R.id.btn_decrease)
        val btnIncrease: ImageButton = view.findViewById(R.id.btn_increase)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_cart, parent, false)
        return CartViewHolder(view)
    }

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        val item = cartItems[position]

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

        holder.itemName.text = item.name
        holder.itemPrice.text = "₹${item.price.toInt()}"
        holder.itemQuantity.text = item.quantity.toString()
        holder.itemTotal.text = "₹${(item.price * item.quantity).toInt()}"

        // Set up click listeners
        holder.btnRemove.setOnClickListener {
            listener.onCartItemRemove(position)
        }

        holder.btnDecrease.setOnClickListener {
            if (item.quantity > 1) {
                listener.onCartItemQuantityChange(position, item.quantity - 1)
            } else {
                listener.onCartItemRemove(position)
            }
        }

        holder.btnIncrease.setOnClickListener {
            listener.onCartItemQuantityChange(position, item.quantity + 1)
        }
    }

    override fun getItemCount() = cartItems.size
}