package com.example.petuk

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.button.MaterialButton

class CartActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var emptyCartView: View
    private lateinit var cartItemsView: View
    private lateinit var tvSubtotal: TextView
    private lateinit var tvTaxes: TextView
    private lateinit var tvDeliveryFee: TextView
    private lateinit var tvTotal: TextView
    private lateinit var tvDiscount: TextView
    private lateinit var etCouponCode: EditText
    private lateinit var btnApplyCoupon: Button
    private lateinit var btnPlaceOrder: MaterialButton

    private val cartItems = mutableListOf<CartItem>()
    private var appliedCoupon: Coupon? = null

    // Constants for taxes and fees
    private val TAX_RATE = 0.05 // 5% tax
    private val DELIVERY_FEE = 40.0 // Fixed delivery fee

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cart)

        // Initialize views
        recyclerView = findViewById(R.id.recycler_cart)
        emptyCartView = findViewById(R.id.empty_cart_view)
        cartItemsView = findViewById(R.id.cart_items_view)
        tvSubtotal = findViewById(R.id.tv_subtotal)
        tvTaxes = findViewById(R.id.tv_taxes)
        tvDeliveryFee = findViewById(R.id.tv_delivery_fee)
        tvTotal = findViewById(R.id.tv_total)
        tvDiscount = findViewById(R.id.tv_discount)
        etCouponCode = findViewById(R.id.et_coupon_code)
        btnApplyCoupon = findViewById(R.id.btn_apply_coupon)
        btnPlaceOrder = findViewById(R.id.btn_place_order)

        // Setup toolbar
        val toolbar = findViewById<androidx.appcompat.widget.Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Your Cart"

        toolbar.setNavigationOnClickListener {
            onBackPressed()
        }

        // Setup recycler view
        recyclerView.layoutManager = LinearLayoutManager(this)
        val cartAdapter = CartAdapter(cartItems, object : CartAdapter.OnCartItemListener {
            override fun onCartItemRemove(position: Int) {
                removeCartItem(position)
            }

            override fun onCartItemQuantityChange(position: Int, quantity: Int) {
                updateItemQuantity(position, quantity)
            }
        })
        recyclerView.adapter = cartAdapter

        // Load cart items from intent
        loadCartItems()

        // Update UI based on cart state
        updateCartUI()

        // Coupon application
        btnApplyCoupon.setOnClickListener {
            val couponCode = etCouponCode.text.toString().trim()
            if (couponCode.isNotEmpty()) {
                applyCoupon(couponCode)
            } else {
                Toast.makeText(this, "Please enter a coupon code", Toast.LENGTH_SHORT).show()
            }
        }

        // Place order button
        btnPlaceOrder.setOnClickListener {
            if (cartItems.isNotEmpty()) {
                Toast.makeText(this, "Order placed successfully!", Toast.LENGTH_SHORT).show()
                // In a real app, you would navigate to a payment screen or order confirmation page
            } else {
                Toast.makeText(this, "Your cart is empty", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun loadCartItems() {
        // Clear existing items
        cartItems.clear()

        // Get cart items from intent
        val cartItemsFromIntent = intent.getParcelableArrayListExtra<CartItem>("CART_ITEMS")
        if (cartItemsFromIntent != null && cartItemsFromIntent.isNotEmpty()) {
            cartItems.addAll(cartItemsFromIntent)
        } else {
            // For demo, let's add some sample items only if there are no items from intent
            cartItems.add(CartItem("1", "Veg Burger", 99.0, true, true, 2))
            cartItems.add(CartItem("3", "French Fries", 79.0, true, true, 1))
            cartItems.add(CartItem("4", "Cold Coffee", 129.0, true, true, 1))
        }
    }

    private fun updateCartUI() {
        if (cartItems.isEmpty()) {
            emptyCartView.visibility = View.VISIBLE
            cartItemsView.visibility = View.GONE
            btnPlaceOrder.isEnabled = false
        } else {
            emptyCartView.visibility = View.GONE
            cartItemsView.visibility = View.VISIBLE
            btnPlaceOrder.isEnabled = true

            recyclerView.adapter?.notifyDataSetChanged()

            // Calculate totals
            val subtotal = calculateSubtotal()
            val taxes = subtotal * TAX_RATE
            val discount = calculateDiscount(subtotal)
            val total = subtotal + taxes + DELIVERY_FEE - discount

            // Update UI
            tvSubtotal.text = "₹${subtotal.toInt()}"
            tvTaxes.text = "₹${taxes.toInt()}"
            tvDeliveryFee.text = "₹${DELIVERY_FEE.toInt()}"
            tvDiscount.text = "-₹${discount.toInt()}"
            tvTotal.text = "₹${total.toInt()}"
        }
    }

    private fun calculateSubtotal(): Double {
        return cartItems.sumOf { it.price * it.quantity }
    }

    private fun calculateDiscount(subtotal: Double): Double {
        return appliedCoupon?.let {
            when (it.type) {
                CouponType.PERCENTAGE -> subtotal * it.value / 100
                CouponType.FIXED -> it.value.coerceAtMost(subtotal)
            }
        } ?: 0.0
    }

    private fun applyCoupon(code: String) {
        // In a real app, this would check against a database or API
        val coupon = when (code.uppercase()) {
            "WELCOME50" -> Coupon("WELCOME50", "50% off", CouponType.PERCENTAGE, 50.0)
            "FLAT100" -> Coupon("FLAT100", "₹100 off", CouponType.FIXED, 100.0)
            "FREESHIP" -> Coupon("FREESHIP", "Free Delivery", CouponType.FIXED, DELIVERY_FEE)
            else -> null
        }

        if (coupon != null) {
            appliedCoupon = coupon
            Toast.makeText(this, "Coupon applied: ${coupon.description}", Toast.LENGTH_SHORT).show()
            updateCartUI()
        } else {
            Toast.makeText(this, "Invalid coupon code", Toast.LENGTH_SHORT).show()
        }
    }

    private fun removeCartItem(position: Int) {
        if (position in cartItems.indices) {
            cartItems.removeAt(position)
            updateCartUI()
        }
    }

    private fun updateItemQuantity(position: Int, quantity: Int) {
        if (position in cartItems.indices) {
            val item = cartItems[position]
            if (quantity <= 0) {
                cartItems.removeAt(position)
            } else {
                item.quantity = quantity
            }
            updateCartUI()
        }
    }
}