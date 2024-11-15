package com.aehs.sge.security

import org.springframework.security.core.Authentication
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.stereotype.Service

@Service
class PermissionService {

    fun hasPermission(authentication: Authentication, permission: String): Boolean {
        return authentication.authorities.contains(SimpleGrantedAuthority(permission));
    }
}