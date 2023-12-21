package com.example.loanapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.content.edit
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
            auth = Firebase.auth

            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener {
                    if (it.isSuccessful) {
                        Log.d(TAG, "loginWithEmail: success")
                        val uid = auth.uid.toString()

                        /*
                        this.getPreferences(MODE_PRIVATE).edit {
                            putString(KEY, uid)
                            commit()
                        }
                         */

                        gotoHomeActivity(uid)
                    } else {
                        Log.d(TAG, "loginWithEmail: failed", it.exception)
                    }
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
            gotoHomeActivity(uid)
        }
         */
    }

    private fun gotoHomeActivity(uid: String) {
        Log.d(TAG, "gotoHomeActivity: process $uid")
        val intent = Intent(this, HomeActivity::class.java)
        intent.putExtra("user_id", uid)
        startActivity(intent)
    }
}