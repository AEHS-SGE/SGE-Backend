package com.aehs.sge.company

import com.aehs.sge.auth.AuthService
import com.aehs.sge.exception.BadRequestException
import com.aehs.sge.exception.ForbiddenException
import com.aehs.sge.exception.NotFoundException
import org.springframework.data.repository.findByIdOrNull
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Service

@Service
class CompanyService(
    private val companyRepository: CompanyRepository,
    private val authService: AuthService,
) {
    // TODO check if this is necessary and if it should be paginated
    fun findAll(): List<Company> {
        return companyRepository.findAll()
    }

    fun findById(id: Long, authentication: Authentication): Company {
        val company = companyRepository.findByIdOrNull(id)
         // TODO perform checks to see if the authenticated user can see the company he's requesting data from.
        return company ?: throw NotFoundException("Company with id $id not found")
    }

    fun create(companyRequest: CreateCompanyRequest): Long {
        if (companyRequest.companyType == CompanyType.BRANCH && companyRequest.headId == null) {
            throw BadRequestException("Head id is required when company type is BRANCH")
        }
        val company = Company(
            companyRequest.name,
            companyRequest.cnpj,
            companyRequest.phoneNumber,
            companyRequest.email,
            companyRequest.companyType,
            if (companyRequest.companyType == CompanyType.BRANCH) companyRequest.headId else null,
        )

        companyRepository.save(company)
        return company.id!!
    }

    fun update(companyId: Long, updateCompanyRequest: UpdateCompanyRequest, authentication: Authentication) {
        val company = companyRepository.findByIdOrNull(companyId)
            ?: throw NotFoundException("Company with id $companyId not found")

        if (!userCanUpdateCompany(company, authentication)) {
            throw ForbiddenException("Company with id $companyId cannot be updated by the current user.")
        }

        company.name = updateCompanyRequest.name
        company.cnpj = updateCompanyRequest.cnpj
        company.email = updateCompanyRequest.email
        company.phone = updateCompanyRequest.phoneNumber

        companyRepository.save(company)
    }

    fun delete() {
        TODO("define how a user is deleted, logical vs physical removal")
    }

    private fun userCanUpdateCompany(company: Company, authUser: Authentication): Boolean {
        val loggedUserCompanyId = authService.getCompanyIdFromAuthentication(authUser)
        val companyId = company.id!!

        return when {
            (companyId == loggedUserCompanyId) -> true
            (company.companyType == CompanyType.BRANCH && company.headId!! == loggedUserCompanyId) -> true
            else -> false
        }
    }
}