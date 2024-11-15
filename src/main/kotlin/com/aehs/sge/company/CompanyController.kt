package com.aehs.sge.company

import com.aehs.sge.auth.AuthService
import org.springframework.http.ResponseEntity
import org.springframework.security.core.Authentication
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/company")
class CompanyController(val companyService: CompanyService, val authService: AuthService) {

    @GetMapping
    fun findAllCompanies(authentication: Authentication): ResponseEntity<List<Company>> {
        val users = companyService.findAll()
        return ResponseEntity.ok(users)
    }

    @GetMapping("{companyId}")
    fun findCompanyById(@PathVariable companyId: Long, authentication: Authentication): ResponseEntity<Company> {
        val company = companyService.findById(companyId)
        return ResponseEntity.ok(company)
    }

    @PostMapping
    fun createCompany(@RequestBody createCompanyRequest: CreateCompanyRequest): ResponseEntity<Long> {
        val companyId = companyService.create(createCompanyRequest)

        return ResponseEntity.ok(companyId)
    }

    @PutMapping("{companyId}")
    fun updateCompany(@PathVariable companyId: Long) {
        TODO("Implement logic to update company")
    }

    @DeleteMapping("{companyId}")
    fun deleteCompany(@PathVariable companyId: Long): ResponseEntity<Unit> {
        TODO("implement logic to delete user")
    }
}