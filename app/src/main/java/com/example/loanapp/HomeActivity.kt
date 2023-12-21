package com.example.loanapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.loanapp.databinding.ActivityHomeBinding

private const val TAG = "HomeActivity"

class HomeActivity : AppCompatActivity() {
    private lateinit var uid: String
    private lateinit var binding: ActivityHomeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        uid = intent.getStringExtra("user_id").toString()
        Log.d(TAG, "userId: $uid")

    }
}