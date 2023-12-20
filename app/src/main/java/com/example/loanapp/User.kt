package com.example.loanapp

data class User(
    val name: String?,
    val email: String?,
    val phoneNumber: String?,
    val password: String?,
    val status: String?
) {
    fun isValid(): Boolean {
        return name != null && email != null && phoneNumber != null && password != null
    }
}
