package com.example.petuk

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.petuk.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var databaseHelper: DatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding= ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        databaseHelper = DatabaseHelper(this)

        val btnLogin = findViewById<Button>(R.id.btnLogin)
        val tvRegister = findViewById<TextView>(R.id.tvRegister)

        btnLogin.setOnClickListener {
            val email = binding.etEmail.text.toString()
            val password = binding.etPassword.text.toString()
            loginDatabase(email, password)
        }

        tvRegister.setOnClickListener {
            startActivity(Intent(this, RegistrationActivity::class.java))
        }

    }
    fun String.isValidEmail(): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(this).matches()
    }
    fun String.isValidPassword(): Boolean {
        return this.length >= 8
    }

    private fun loginDatabase(email: String, password: String) {



        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(applicationContext, "Please fill all fields", Toast.LENGTH_SHORT).show()
            return
        }

        val userExists = databaseHelper.readUser(email, password)
        if (userExists) {
            Toast.makeText(applicationContext,"Logged in Successfully", Toast.LENGTH_SHORT).show()
            startActivity(Intent(this@LoginActivity, HomeActivity::class.java))
            finish()
        } else {
            Toast.makeText(applicationContext,"Login Failed", Toast.LENGTH_SHORT).show()
        }
    }
}