package com.example.loanapp

data class User(
    val name: String,
    val email: String,
    val phoneNumber: String,
    var password: String,
    val records: MutableMap<*, *>,
    val type: String
) {
    constructor(user: Map<String, Any>) : this(
        user["name"].toString(),
        user["email"].toString(),
        user["phoneNumber"].toString(),
        user["password"].toString(),
        user["records"] as MutableMap<*, *>,
        user["type"].toString()
    )

    fun verify() = name.isNotEmpty()
            && email.isNotEmpty()
            && phoneNumber.isNotEmpty()
            && password.isNotEmpty()
            && type.isNotEmpty()

}
