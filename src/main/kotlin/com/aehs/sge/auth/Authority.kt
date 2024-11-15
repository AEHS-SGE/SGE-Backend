package com.aehs.sge.auth

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import org.springframework.security.core.GrantedAuthority

@Entity
class Authority (
    @Column(name = "description") var description: String,
    @Id @Column(name = "authority_id") var authorityName: String
) : GrantedAuthority {
    override fun getAuthority(): String {
        return authorityName
    }
}