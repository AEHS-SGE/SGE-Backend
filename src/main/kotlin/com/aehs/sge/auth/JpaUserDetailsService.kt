package com.aehs.sge.auth

import com.aehs.sge.user.User
import com.aehs.sge.user.UserRepository
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

@Service
class JpaUserDetailsService(val userRepository: UserRepository) : UserDetailsService {

    override fun loadUserByUsername(email: String): UserDetails {
        val user: User? = userRepository.findByEmail(email)

        if (user != null) {
            return AuthUser(user)
        } else {
            throw UsernameNotFoundException("User not found")
        }
    }
}
