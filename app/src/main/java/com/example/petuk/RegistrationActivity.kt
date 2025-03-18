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
import com.example.petuk.databinding.ActivityRegistrationBinding

class RegistrationActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegistrationBinding
    private lateinit var databaseHelper: DatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding= ActivityRegistrationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        databaseHelper = DatabaseHelper(this)

        val btnRegister = findViewById<Button>(R.id.btnRegister)
        val tvLogin = findViewById<TextView>(R.id.tvLogin)

        btnRegister.setOnClickListener {
            val name= binding.etFullName.text.toString()
            val email = binding.etEmail.text.toString()
            val password = binding.etPassword.text.toString()
            registerDatabase(name, email, password)
        }

        tvLogin.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        }

    }
    private fun registerDatabase(name:String, email: String, password: String) {
        val insertedRowid = databaseHelper.insertUser(name, email, password)
        if (insertedRowid != -1L){
            Toast.makeText(applicationContext, "Registration Successful", Toast.LENGTH_SHORT).show()
            startActivity(Intent(this@RegistrationActivity, LoginActivity::class.java))
            finish()
        } else {
            Toast.makeText(applicationContext, "Signup Failed", Toast.LENGTH_SHORT).show()
        }
    }
}