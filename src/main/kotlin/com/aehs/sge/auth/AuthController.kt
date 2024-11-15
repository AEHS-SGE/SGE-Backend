package com.aehs.sge.auth

import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("api/auth")
class AuthController(val authService: AuthService, val authManager: AuthenticationManager) {
    @PostMapping("login")
    fun login(@RequestBody userLogin: LoginRequest): ResponseEntity<*> {
        val usernamePasswordAuthenticationToken = UsernamePasswordAuthenticationToken(userLogin.email, userLogin.password)

        val authentication = authManager.authenticate(usernamePasswordAuthenticationToken)

        SecurityContextHolder.getContext().authentication = authentication
        val token = authService.generateToken(authentication)

        LoginResponse("User Logged in successfully!", token)

        return ResponseEntity.ok(token)
    }
}