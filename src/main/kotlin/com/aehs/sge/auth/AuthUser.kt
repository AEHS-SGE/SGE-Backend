package com.aehs.sge.auth

import com.aehs.sge.user.User
import org.springframework.security.core.userdetails.UserDetails


class AuthUser(var user: User) : UserDetails {
    override fun getAuthorities(): MutableCollection<Authority> {
        return user.authorities
    }

    override fun getPassword(): String {
        return user.password
    }

    override fun getUsername(): String {
        return user.email
    }

}