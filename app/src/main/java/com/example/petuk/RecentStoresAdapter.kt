package com.example.petuk

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class RecentStoresAdapter(private val context: Context) :
    RecyclerView.Adapter<RecentStoresAdapter.StoreViewHolder>() {

    private val stores = mutableListOf<StoreItem>()

    class StoreViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val cardView: CardView = view.findViewById(R.id.card_store)
        val storeIdText: TextView = view.findViewById(R.id.tv_store_id)
        val dateText: TextView = view.findViewById(R.id.tv_store_date)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StoreViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_recent_store, parent, false)
        return StoreViewHolder(view)
    }

    override fun onBindViewHolder(holder: StoreViewHolder, position: Int) {
        val store = stores[position]
        holder.storeIdText.text = "Store ID: ${store.storeId}"

        // Format date
        val dateFormat = SimpleDateFormat("dd MMM yyyy, HH:mm", Locale.getDefault())
        val date = Date(store.timestamp)
        holder.dateText.text = dateFormat.format(date)

        // Set click listener to open the store
        holder.cardView.setOnClickListener {
            val intent = Intent(context, EnterStore::class.java)
            intent.putExtra("STORE_ID", store.storeId)
            context.startActivity(intent)
        }
    }

    override fun getItemCount() = stores.size

    fun updateStores(newStores: List<StoreItem>) {
        stores.clear()
        stores.addAll(newStores)
        notifyDataSetChanged()
    }
}