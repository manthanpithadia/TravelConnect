package com.example.travelconnect.views
import MainViewModel
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.travelconnect.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {
    lateinit var binding: ActivityLoginBinding
    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

      //  viewModel = ViewModelProvider(this, ViewModelFactory(applicationContext)).get(MainViewModel::class.java)
        viewModel = ViewModelProvider(this)[MainViewModel::class.java]

        binding.txtRegister.setOnClickListener {
            startActivity(Intent(this, ActivitySignup::class.java))
        }
        viewModel.loginResultLiveData.observe(this) { loginSuccessful ->
            if (loginSuccessful) {
                // Handle successful login
                Toast.makeText(this, "Sign up successful", Toast.LENGTH_SHORT).show()
               // Log.i("Log", "Done")
            } else {
                // Handle login failure
                Toast.makeText(this, "Sign up failed", Toast.LENGTH_SHORT).show()
               // Log.i("Log", "Failed")
            }
        }

// Trigger the login operation in the ViewModel

        binding.button.setOnClickListener {
            val username = binding.usernameEditText.text.toString()
            val password = binding.edtPassword.text.toString()
            viewModel.performLogin()
        }
    }
}