package com.aehs.sge.company

import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import org.hibernate.validator.constraints.br.CNPJ

data class CreateCompanyRequest(
    @field:NotBlank val name: String,
    @field:CNPJ val cnpj: String,
    @field:NotBlank val phoneNumber: String,
    @field:Email val email: String,
    @field:NotNull val companyType: CompanyType,
    val headId: Long?
)

class UpdateCompanyRequest(
    val name: String,
    val cnpj: String,
    val phoneNumber: String,
    val email: String,
)
