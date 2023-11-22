package com.example.travelconnect.views

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.travelconnect.ActivityNavigation
import com.example.travelconnect.R
import com.example.travelconnect.databinding.ActivitySignupBinding
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
                Toast.makeText(this, "Sign up successful", Toast.LENGTH_SHORT).show()
                startActivity(Intent(this,ActivityNavigation::class.java))
            } else {
                // Handle login failure
                Toast.makeText(this, "Sign up failed", Toast.LENGTH_SHORT).show()
                // Log.i("Log", "Failed")
            }
        }

        // Sign up button
        binding.btnSignUp.setOnClickListener {
            viewModel.performSignup(binding.signupUname.toString(), binding.signupPass.toString())
        }
    }
}