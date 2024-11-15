package com.aehs.sge.user

import jakarta.validation.constraints.NotEmpty

data class UserResponse(val id: Long, val name: String, val email: String, val companyId: Long) {
    constructor(user: User) : this(user.id!!, user.name, user.email, user.company.id!!)
}

data class CreateUserRequest (@NotEmpty val name: String, @NotEmpty val email: String, @NotEmpty val password: String, @NotEmpty val authorities: List<String>)