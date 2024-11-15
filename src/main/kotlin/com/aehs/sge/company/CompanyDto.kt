package com.aehs.sge.company

data class CreateCompanyRequest(
    val name: String,
    val cnpj: String,
    val phoneNumber: String,
    val email: String,
    val companyType: CompanyType,
    val headId: Long?
)

class UpdateCompanyRequest(
    val name: String,
    val cnpj: String,
    val phoneNumber: String,
    val email: String,
)
