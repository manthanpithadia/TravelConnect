package com.example.travelconnect.views

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.travelconnect.R
import com.example.travelconnect.databinding.ActivitySignupBinding
import com.example.travelconnect.viewmodels.SignupViewModel

class ActivitySignup : AppCompatActivity() {

    lateinit var binding: ActivitySignupBinding

    private lateinit var viewModel: SignupViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        binding = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel = ViewModelProvider(this)[SignupViewModel::class.java]

        viewModel.signupResultLiveData.observe(this) { signupSuccessful ->
            if (signupSuccessful) {
                // Handle successful login
                Toast.makeText(this, "Sign up successful", Toast.LENGTH_SHORT).show()
                // Log.i("Log", "Done")
            } else {
                // Handle login failure
                Toast.makeText(this, "Sign up failed", Toast.LENGTH_SHORT).show()
                // Log.i("Log", "Failed")
            }
        }

        // Sign up button
        binding.button.setOnClickListener {
            viewModel.performSignup()
        }
    }
}