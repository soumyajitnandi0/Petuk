package com.example.petuk

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class EnterStore : AppCompatActivity() {

    private var storeId: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_enter_store)

        // Retrieve the store ID from intent
        storeId = intent.getStringExtra("STORE_ID") ?: ""

        val bottomNav = findViewById<BottomNavigationView>(R.id.bottom_navigation)

        // Set default fragment (Menu)
        replaceFragment(MenuFragment.newInstance(storeId))

        bottomNav.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.menuFragment -> replaceFragment(MenuFragment.newInstance(storeId))
                R.id.historyFragment -> replaceFragment(OrderHistoryFragment.newInstance(storeId))
                R.id.storeFragment -> replaceFragment(StoreDetailsFragment.newInstance(storeId))
            }
            true
        }
    }

    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.nav_host_fragment, fragment)
            .commit()
    }
}