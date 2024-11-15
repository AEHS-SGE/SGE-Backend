package com.aehs.sge.user

import org.springframework.http.ResponseEntity
import org.springframework.security.core.Authentication
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("api/v1/users")
class UserController(val userService: UserService) {

    @GetMapping
    fun findAllUsers(authentication: Authentication): ResponseEntity<List<User>> {
        val users = userService.findAllFromCompany(authentication)
        return ResponseEntity.ok(users)
    }

    @GetMapping("{userId}")
    fun findUserById(@PathVariable userId: Long, authentication: Authentication): ResponseEntity<User> {
        val user = userService.findById(userId, authentication)
        return ResponseEntity.ok(user)
    }

    @PostMapping
    fun createUser(@RequestBody userRequest: CreateUserRequest, authentication: Authentication): ResponseEntity<Long> {
        val userId = userService.create(userRequest, authentication)

        return ResponseEntity.ok(userId)
    }

    @PutMapping("{userId}")
    fun updateUser(@PathVariable userId: Long, authentication: Authentication) {
        TODO("Implement logic to update user")
    }

    @DeleteMapping("{userId}")
    fun deleteUser(@PathVariable userId: Long, authentication: Authentication): ResponseEntity<String> {
        TODO("implement logic to delete user")
    }
}