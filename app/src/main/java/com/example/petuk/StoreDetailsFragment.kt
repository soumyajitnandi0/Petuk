package com.example.petuk

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RatingBar
import android.widget.TextView
import androidx.cardview.widget.CardView

class StoreDetailsFragment : Fragment() {

    private var storeId: String = ""

    companion object {
        fun newInstance(storeId: String): StoreDetailsFragment {
            val fragment = StoreDetailsFragment()
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
        val view = inflater.inflate(R.layout.fragment_store_details, container, false)

        // In a real app, you would fetch these details from an API based on the storeId
        // For demonstration, we'll just set hardcoded values

        val tvStoreName = view.findViewById<TextView>(R.id.tv_store_name)
        val tvLocation = view.findViewById<TextView>(R.id.tv_location)
        val tvVendorName = view.findViewById<TextView>(R.id.tv_vendor_name)
        val ratingBar = view.findViewById<RatingBar>(R.id.rating_bar)
        val tvReviewCount = view.findViewById<TextView>(R.id.tv_review_count)
        val tvDescription = view.findViewById<TextView>(R.id.tv_description)

        // Set sample data
        tvStoreName.text = "Food Paradise #$storeId"
        tvLocation.text = "123 Main Street, City Center"
        tvVendorName.text = "Vendor: Food Corp Ltd."
        ratingBar.rating = 4.5f
        tvReviewCount.text = "(235 reviews)"
        tvDescription.text = "A popular food joint serving delicious fast food since 2010. Known for burgers, fries, and refreshing beverages. Rated highly for cleanliness and quick service."

        return view
    }
}