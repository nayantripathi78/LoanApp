package com.example.loanapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.loanapp.databinding.ActivitySignupBinding
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

private const val TAG = "SignupActivity"

class SignupActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignupBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var userType: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = Firebase.auth

        binding.signupButton.setOnClickListener {
            val records = mutableMapOf<String,Any>("status" to "clear")

            val user = User(
                binding.userName.text.toString(),
                binding.userEmail.text.toString(),
                binding.userPhoneNumber.text.toString(),
                binding.userPassword.text.toString(),
                records,
                userType
            )

            if (user.verify()) {
                Log.d(TAG, "createUserWithEmailAndPassword: onProcess")

                auth.createUserWithEmailAndPassword(user.email, user.password)
                    .addOnCompleteListener {
                        if (it.isSuccessful) {
                            Log.d(TAG, "createUserWithEmailAndPassword: success")

                            //launch coroutine for add user on database
                            CoroutineScope(Dispatchers.IO).launch {
                                user.password = "*****"
                                addUserOnDatabase(user, auth.uid!!)
                            }
                            goToLoginActivity()
                        } else {
                            Log.d(TAG, "createUserWithEmailAndPassword: failed", it.exception)
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

    private fun addUserOnDatabase(user: User, userId: String) {
        Log.d(TAG, "addUserOnDatabase: onProcess")
        val db = Firebase.firestore
        //add user on users collection
        db.collection("users")
            .document(userId)
            .set(user)
            .addOnSuccessListener {
                Log.d(TAG, "addUserOnUsersCollection: success")
            }
            .addOnFailureListener {
                Log.d(TAG, "addUserOnUsersCollection: failed", it.cause)
            }
        //add user on lender/borrower collection
        db.collection(user.type)
            .document(userId)
            .set(user)
            .addOnSuccessListener {
                Log.d(TAG, "addUserOn${user.type}Collection: success")
            }
            .addOnFailureListener {
                Log.d(TAG, "addUserOn${user.type}Collection: failed", it.cause)

            }
    }

    private fun goToLoginActivity() {
        Log.d(TAG, "goToLoginActivity: onProcess")
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
    }
}