package com.example.travelconnect.views

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.travelconnect.ActivityNavigation
import com.example.travelconnect.R
import com.example.travelconnect.databinding.ActivitySignupBinding
import com.example.travelconnect.utils.isValidEmail
import com.example.travelconnect.utils.isValidPassword
import com.example.travelconnect.viewmodels.SignupViewModel
import com.example.travelconnect.viewmodels.SignupViewModelFactory

class ActivitySignup : AppCompatActivity() {

    lateinit var binding: ActivitySignupBinding

    //private lateinit var viewModel: SignupViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        binding = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val viewModel: SignupViewModel by viewModels { SignupViewModelFactory(application) }

        viewModel.signupResultLiveData.observe(this) { signupSuccessful ->
            if (signupSuccessful) {
                // Handle successful login
                Toast.makeText(this, "User registered successfully", Toast.LENGTH_SHORT).show()
                startActivity(Intent(this,LoginActivity::class.java))
            } else {
                // Handle login failure
                Toast.makeText(this, "Sign up failed", Toast.LENGTH_SHORT).show()
                // Log.i("Log", "Failed")
            }
        }

        // Sign up button
        binding.btnSignUp.setOnClickListener {
            val email = binding.signupUname.text.toString()
            val password = binding.signupPass.text.toString()

            if (email.isValidEmail() && password.isValidPassword()) {
                // Both email and password are valid, proceed with sign up
                viewModel.performSignup(email, password)
            } else {
                Toast.makeText(this, "Incorrect Format", Toast.LENGTH_SHORT).show()
            }

        }
    }
}