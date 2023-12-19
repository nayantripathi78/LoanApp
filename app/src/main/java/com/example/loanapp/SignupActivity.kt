package com.example.loanapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.loanapp.databinding.ActivitySignupBinding
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth

private const val TAG = "SignupActivity"

class SignupActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignupBinding
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = Firebase.auth

        binding.signupButton.setOnClickListener {
            val email = binding.userEmail.text.toString()
            val password = binding.userPassword.text.toString()

            auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener {
                    if (it.isSuccessful) {
                        Log.d(TAG, "signupWithEmail: success")
                        Toast.makeText(this, "success", Toast.LENGTH_SHORT).show()
                    } else {
                        Log.d(TAG, "signupWithEmail: failed", it.exception)
                    }
                }
        }
    }
}