package com.example.petuk

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.petuk.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var databaseHelper: DatabaseHelper
    private val TAG = "LoginActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        try {
            databaseHelper = DatabaseHelper(this)
            Log.d(TAG, "Database helper initialized")
        } catch (e: Exception) {
            Log.e(TAG, "Error initializing database helper", e)
            Toast.makeText(this, "Database initialization error", Toast.LENGTH_LONG).show()
        }

        binding.btnLogin.setOnClickListener {
            try {
                val email = binding.etEmail.text.toString().trim()
                val password = binding.etPassword.text.toString().trim()
                Log.d(TAG, "Login attempt for email: $email")
                loginDatabase(email, password)
            } catch (e: Exception) {
                Log.e(TAG, "Error during login button click", e)
                Toast.makeText(this, "Login button error: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        }

        binding.tvRegister.setOnClickListener {
            try {
                startActivity(Intent(this, RegistrationActivity::class.java))
            } catch (e: Exception) {
                Log.e(TAG, "Error navigating to registration", e)
                Toast.makeText(this, "Navigation error", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun loginDatabase(email: String, password: String) {
        try {
            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
                return
            }

            // Debug: print all users in database
            val allUsers = databaseHelper.getAllUsers()
            Log.d(TAG, "All users in database: $allUsers")

            val userExists = databaseHelper.readUser(email, password)
            Log.d(TAG, "User exists check result: $userExists")

            if (userExists) {
                Toast.makeText(this, "Logged in Successfully", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, HomeActivity::class.java)
                startActivity(intent)
                finish()
            } else {
                Toast.makeText(this, "Login Failed - Invalid credentials", Toast.LENGTH_SHORT).show()
            }
        } catch (e: Exception) {
            Log.e(TAG, "Error in loginDatabase function", e)
            Toast.makeText(this, "Login process error: ${e.message}", Toast.LENGTH_SHORT).show()
        }
    }
}