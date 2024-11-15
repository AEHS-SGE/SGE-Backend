package com.aehs.sge.user

import com.aehs.sge.auth.AuthService
import com.aehs.sge.auth.AuthorityRepository
import com.aehs.sge.company.CompanyRepository
import com.aehs.sge.exception.DuplicatedException
import com.aehs.sge.exception.NotFoundException
import org.springframework.data.repository.findByIdOrNull
import org.springframework.security.core.Authentication
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class UserService(
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder,
    private val companyRepository: CompanyRepository,
    private val authorityRepository: AuthorityRepository,
    private val authService: AuthService,
) {

    /**
     * Fetches a list of all users from the company of the currently authenticated user.
     * If the company is a HEAD, also fetches users from the BRANCHES.
     * TODO add filter to check if users from the BRANCHES should also be returned.
     */
    fun findAllFromCompany(authentication: Authentication): List<User> {
        val companyId = authService.getCompanyIdFromAuthentication(authentication)
        return userRepository.findAllByCompanyId(companyId)
    }

    /**
     * Finds a single user based on the provided id.
     */
    fun findById(id: Long, authentication: Authentication): User {
        val companyId = authService.getCompanyIdFromAuthentication(authentication)
        val user = userRepository.findByIdOrNull(id)

        if (user == null || user.company.id != companyId) {
            throw NotFoundException("User with id $id not found")
        } else {
            return user
        }
    }

    /**
     * Creates a new user and return the newly created user id.
     */
    fun create(userRequest: CreateUserRequest, authentication: Authentication): Long  {
        val existingUser = userRepository.findByEmail(userRequest.email)
        if (existingUser != null) {
            throw DuplicatedException("This user already exists")
        }

        val company = companyRepository.getReferenceById(authService.getCompanyIdFromAuthentication(authentication))
        val authorities = userRequest.authorities.map { authority -> authorityRepository.getReferenceById(authority) }.toMutableList()
        var user = User(
            userRequest.name,
            userRequest.email,
            passwordEncoder.encode(userRequest.password),
            company,
            authorities
        )

        user = userRepository.save(user)

        return user.id!!
    }

    fun update() {
        TODO("define what properties of a user can be updated")
    }

    fun delete() {
        TODO("define how a user is deleted, logical vs physical removal")
    }
}