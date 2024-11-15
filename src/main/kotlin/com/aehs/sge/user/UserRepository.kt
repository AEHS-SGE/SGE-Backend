package com.aehs.sge.user

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface UserRepository : JpaRepository<User, Long> {
    fun findByEmail(email: String): User?

    @Query("Select u from User u where u.company.id = :id")
    fun findAllByCompanyId(id: Long): List<User>
}