package com.aehs.sge.auth

import com.aehs.sge.user.UserRepository
import org.springframework.security.core.Authentication
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RequestMapping("/test")
@RestController
class TestController(val userRepository: UserRepository) {
//    @PreAuthorize("hasRole('ROLE_SUPER_ADMIN')")
    @GetMapping
    fun test(authentication: Authentication?): String {


        if (authentication != null) {
            val usuario = userRepository.findByEmail(authentication.name)
            println(usuario?.email)

            return "Hello ${authentication.name}"
        }

        return "Hello unknown"
    }
}