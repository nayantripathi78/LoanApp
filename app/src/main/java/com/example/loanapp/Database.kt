package com.example.loanapp

import android.util.Log
import com.google.firebase.Firebase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

private const val TAG = "DataBase"

class Database {
    private lateinit var db: FirebaseFirestore
    fun addUserOnDatabase(uid: String, user: User) {
        db = Firebase.firestore

        //add user on users collection
        db.collection("users")
            .document(uid)
            .set(user)
            .addOnSuccessListener {
                Log.d(TAG, "addUserOnDatabase(\"users\"): success")
            }
            .addOnFailureListener {
                Log.d(TAG, "addUserOnDatabase(\"users\"): fail")
            }

        //add user on their group collection
        db.collection(user.type)
            .document(uid)
            .set(user)
            .addOnSuccessListener {
                Log.d(TAG, "addUserOnDatabase(\"${user.type}\"): success")
            }
            .addOnFailureListener {
                Log.d(TAG, "addUserOnDatabase(\"${user.type}\"): fail")
            }
    }

    fun getUserFromDatabase(userId: String): User {
        val task = Firebase.firestore
            .collection("users")
            .document(userId)
            .get()
            .addOnSuccessListener {

            }
            .addOnFailureListener {

            }
        return User(task.result.data!!)
    }

    fun getBorrowerListFromDatabase(): Unit {
        db = Firebase.firestore
        db.collection("borrowers")
            .get()
            .addOnSuccessListener {

            }
            .addOnFailureListener {

            }
    }
}