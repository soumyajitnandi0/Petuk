package com.example.petuk

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.content.Intent
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast


class StoreFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_store, container, false)

        val storeIdInput = view.findViewById<EditText>(R.id.et_store_id)
        val enterButton = view.findViewById<Button>(R.id.btn_enter_store)

        enterButton.setOnClickListener {
            val storeId = storeIdInput.text.toString().trim()
            if (storeId.isNotEmpty()) {
                Toast.makeText(requireContext(), "Fetching store details for ID: $storeId", Toast.LENGTH_SHORT).show()

                // Create intent to launch EnterStore activity
                val intent = Intent(requireContext(), EnterStore::class.java)
                intent.putExtra("STORE_ID", storeId)
                startActivity(intent)
            } else {
                Toast.makeText(requireContext(), "Enter a valid STORE ID", Toast.LENGTH_SHORT).show()
            }
        }

        return view
    }
}