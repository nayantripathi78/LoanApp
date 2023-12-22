package com.example.loanapp

data class User(
    val name: String,
    val email: String,
    val phoneNumber: String,
    val password: String,
    val status: String,
    val type: String
) {
    constructor(user: Map<String, Any>) : this(
        user["name"].toString(),
        user["email"].toString(),
        user["phoneNumber"].toString(),
        user["password"].toString(),
        user["status"].toString(),
        user["type"].toString()
    )
}
