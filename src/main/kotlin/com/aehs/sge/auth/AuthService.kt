package com.aehs.sge.auth

import com.aehs.sge.exception.NotFoundException
import com.aehs.sge.user.User
import com.aehs.sge.user.UserNotFoundException
import com.aehs.sge.user.UserRepository
import com.aehs.sge.user.UserService
import org.springframework.security.core.Authentication
import org.springframework.security.oauth2.jwt.*
import org.springframework.stereotype.Service
import java.time.Instant
import java.time.temporal.ChronoUnit

@Service
class AuthService(
    private val jwtEncoder: JwtEncoder,
    private val userService: UserService,
    private val userRepository: UserRepository
) {
    fun generateToken(authentication: Authentication): String{
        val now = Instant.now()

        val loggedUser = findUserFromAuthentication(authentication)

        val scope: String = authentication.authorities.map { it.authority }.joinToString( " ")
        val claims: JwtClaimsSet = JwtClaimsSet.builder()
            .issuer("self")
            .issuedAt(now)
            .expiresAt(now.plus(100, ChronoUnit.HOURS))
            .subject(authentication.name)
            .claim("scope", scope)
            .claim("company_id", loggedUser.company.id)
            .build()
        val header = JwsHeader.with { "HS256" }.build()

        return jwtEncoder.encode(JwtEncoderParameters.from(header, claims)).tokenValue
    }

    fun findUserFromAuthentication(authentication: Authentication): User {
        return userRepository.findByEmail(authentication.name) ?: throw NotFoundException("Could not resolve current user")
    }

    fun getCompanyIdFromAuthentication(authentication: Authentication): Long {
        val claims = (authentication.principal as Jwt).claims
        return (claims["company_id"] as String).toLong()
    }
}
