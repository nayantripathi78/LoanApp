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
    private lateinit var db: Database
    private lateinit var userType: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = Firebase.auth

        binding.signupButton.setOnClickListener {

            val user = User(
                binding.userName.text.toString(),
                binding.userEmail.text.toString(),
                binding.userPhoneNumber.text.toString(),
                binding.userPassword.text.toString(),
                "clear",
                userType
            )

            if (user.email.isNotEmpty() and user.password.isNotEmpty()) {
                auth.createUserWithEmailAndPassword(user.email!!, user.password!!)
                    .addOnCompleteListener {
                        if (it.isSuccessful) {
                            Log.d(TAG, "signupWithEmail: success")
                            db = Database()
                            db.addUserOnDatabase(auth.uid!!, user)
                            Toast.makeText(this, "success", Toast.LENGTH_SHORT).show()
                        } else {
                            Log.d(TAG, "signupWithEmail: failed", it.exception)
                        }
                    }
            } else {
                Toast.makeText(this, "Enter valid input", Toast.LENGTH_SHORT).show()
            }
        }
        binding.radioLender.setOnClickListener {
            userType = "lender"
        }
        binding.radioBorrower.setOnClickListener {
            userType = "borrower"
        }
    }
}