package com.example.loanapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import com.example.loanapp.databinding.ActivityHomeBinding
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

private const val TAG = "HomeActivity"

class HomeActivity : AppCompatActivity() {
    private lateinit var userId: String
    private lateinit var binding: ActivityHomeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.progressBar.visibility = View.VISIBLE
        userId = intent.getStringExtra("user_id").toString()

        CoroutineScope(Dispatchers.IO).launch {
            Log.d(TAG, "launchCoroutine: success")

            val user = getUserFromDatabase(userId)
            if (user != null) openFragment(user.type) else {
                Log.d(TAG, "getUserFromDatabase: result: user == null")
                finish()
            }
        }

    }

    private suspend fun getUserFromDatabase(uid: String): User? {
        Log.d(TAG, "getUserFromDatabase: onProcess")

        val task = Firebase.firestore
            .collection("users")
            .document(uid)
            .get()
            .addOnSuccessListener {
                Log.d(TAG, "getUserFromDatabase: success")
            }
            .addOnFailureListener {
                Log.d(TAG, "getUserFromDatabase: failed", it.cause)
            }
        task.await()
        return if (task.isSuccessful) User(task.result.data!!) else null
    }

    private fun openFragment(userType: String) {
        Log.d(TAG, "open${userType}Fragment: onProcess")

        binding.progressBar.visibility = View.INVISIBLE
        val fragment: Fragment = when (userType) {
            "lender" -> LenderFragment()
            "borrower" -> BorrowerFragment()
            else -> Fragment()
        }

        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragmentFrame, fragment)
            .commit()
    }
}