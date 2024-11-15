package com.aehs.sge

import com.aehs.sge.auth.Authority
import com.aehs.sge.auth.AuthorityRepository
import com.aehs.sge.company.Company
import com.aehs.sge.company.CompanyRepository
import com.aehs.sge.company.CompanyType
import com.aehs.sge.user.User
import com.aehs.sge.user.UserRepository
import org.springframework.boot.ApplicationArguments
import org.springframework.boot.ApplicationRunner
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Component

@ConditionalOnProperty(value = ["data.seed.enabled"], havingValue = "true")
@Component
class TestDataSeeder(
    val authorityRepository: AuthorityRepository,
    val userRepository: UserRepository,
    val companyRepository: CompanyRepository,
    val passwordEncoder: PasswordEncoder
) : ApplicationRunner {
    override fun run(args: ApplicationArguments?) {
        val authorities = mutableListOf(
            Authority("User permission", "USER"),
            Authority("Admin permission", "ADMIN")
        )

        authorityRepository.saveAll(authorities)

        val company = companyRepository.save(
            Company(
                "My company",
                "00.623.904/0001-73",
                "00000000",
                "company@email.com",
                CompanyType.HEAD
            )
        )
        val usuario = User(
            "Dummy user",
            "dummy.user@dummy.com",
            passwordEncoder.encode("dummy"),
            company,
            authorities
        )

        userRepository.save(usuario)
    }
}