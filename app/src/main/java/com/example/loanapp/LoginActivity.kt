package com.example.loanapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.loanapp.databinding.ActivityLoginBinding
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth

private const val TAG = "LoginActivity"

class LoginActivity : AppCompatActivity() {
    private val KEY = "user_id"
    private lateinit var binding: ActivityLoginBinding
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.loginButton.setOnClickListener {
            val email = binding.userEmail.text.toString()
            val password = binding.userPassword.text.toString()

            if (email.isNotEmpty() && password.isNotEmpty()) {
                Log.d(TAG, "signInWithEmailAndPassword: onProcess")
                auth = Firebase.auth
                auth.signInWithEmailAndPassword(email, password)
                    .addOnSuccessListener {
                        Log.d(TAG, "signInWithEmailAndPassword: success")
                        val userId = auth.uid.toString()
                        /*
                        this.getPreferences(MODE_PRIVATE).edit {
                            putString(KEY, uid)
                            commit()
                        }
                         */
                        goToHomeActivity(userId)
                    }
                    .addOnFailureListener {
                        Log.d(TAG, "signInWithEmailAndPassword: failed", it.cause)
                    }
            } else {
                Toast.makeText(this, "Enter valid input", Toast.LENGTH_SHORT).show()
            }
        }

        binding.gotoSignupPage.setOnClickListener {
            val intent = Intent(this, SignupActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onStart() {
        super.onStart()
        /*
        val sharedPref = this.getPreferences(MODE_PRIVATE)
        val uid = sharedPref.getString(KEY, null)
        if (uid != null) {
            goToHomeActivity(uid)
        }
         */
    }

    private fun goToHomeActivity(userId: String) {
        Log.d(TAG, "goToHomeActivity: onProcess")
        val intent = Intent(this, HomeActivity::class.java)
        //pass user id to home activity
        intent.putExtra("user_id", userId)
        startActivity(intent)
    }
}