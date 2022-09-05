package com.samsung.healthcare.account.adapter.auth.supertoken

data class SignInResponse(val user: User)

data class User(val id: String, val email: String)
