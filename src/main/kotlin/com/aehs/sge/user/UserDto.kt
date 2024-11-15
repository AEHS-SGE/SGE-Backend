package com.aehs.sge.user

import jakarta.validation.constraints.NotBlank

data class CreateUserRequest (
    @field:NotBlank val name: String,
    @field:NotBlank val email: String,
    @field:NotBlank val password: String,
    @field:NotBlank val authorities: List<String>
)

data class UserResponse(val id: Long, val name: String, val email: String, val companyId: Long) {
    constructor(user: User) : this(user.id!!, user.name, user.email, user.company.id!!)
}
