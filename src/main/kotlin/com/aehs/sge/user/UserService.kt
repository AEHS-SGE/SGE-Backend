package com.aehs.sge.user

import com.aehs.sge.auth.AuthorityRepository
import com.aehs.sge.company.CompanyRepository
import com.aehs.sge.exception.DuplicatedException
import com.aehs.sge.exception.NotFoundException
import org.springframework.data.repository.findByIdOrNull
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class UserService(
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder,
    private val companyRepository: CompanyRepository,
    private val authorityRepository: AuthorityRepository,
) {

    /**
     * Fetches a list of all users from the company of the currently authenticated user.
     * If the company is a HEAD, also fetches users from the BRANCHES.
     * TODO add filter to check if users from the BRANCHES should also be returned.
     */
    fun findAllFromCompany(companyId: Long): List<UserResponse> {
        return userRepository.findAllByCompanyId(companyId).map { UserResponse(it) }
    }

    /**
     * Finds a single user based on the provided id.
     */
    fun findById(id: Long, companyId: Long): UserResponse {
        val user = userRepository.findByIdOrNull(id)

        if (user == null || user.company.id != companyId) {
            throw NotFoundException("User with id $id not found")
        } else {
            return UserResponse(user)
        }
    }

    /**
     * Creates a new user and return the newly created user's id.
     */
    fun create(userRequest: CreateUserRequest, companyId: Long): Long  {
        val existingUser = userRepository.findByEmail(userRequest.email)
        if (existingUser != null) {
            throw DuplicatedException("This user already exists")
        }

        val company = companyRepository.getReferenceById(companyId)

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