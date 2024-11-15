package com.aehs.sge.user

import com.aehs.sge.auth.Authority
import com.aehs.sge.company.Company
import jakarta.persistence.*
import jakarta.validation.constraints.NotEmpty

@Entity
@Table(name = "user_tb")
class User (
    @Column var name: String,
    @Column var email: String,
    @Column var password: String,
    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "company_id") var company: Company,
    @ManyToMany
        @JoinTable(name = "user_authority",
            joinColumns = [JoinColumn(name = "user_id", referencedColumnName = "user_id")],
            inverseJoinColumns =  [JoinColumn(name = "authority_id", referencedColumnName = "authority_id")]
        ) var authorities: MutableCollection<Authority>,
    @Id @Column(name = "user_id") @GeneratedValue(strategy = GenerationType.IDENTITY) var id: Long? = null)

data class CreateUserRequest (@NotEmpty val name: String, @NotEmpty val email: String, @NotEmpty val password: String, @NotEmpty val authorities: List<String>)