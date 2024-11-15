package com.aehs.sge.auth

data class LoginRequest(val email: String, val password: String)

data class LoginResponse(val message: String, val token: String)
