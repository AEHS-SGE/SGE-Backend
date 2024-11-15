package com.aehs.sge.company

import jakarta.persistence.*

@Entity
@Table(name = "company")
class Company(
    @Column var name: String,
    @Column var cnpj: String,
    @Column(name = "phone_number") var phone: String,
    @Column var email: String,
    @Column(name = "company_type") @Enumerated(EnumType.STRING) var companyType: CompanyType,
    @Column(name = "head_id")  var headId: Long? = null,
    @Column(name = "company_id") @Id @GeneratedValue(strategy = GenerationType.IDENTITY) var id: Long? = null,
)

enum class CompanyType {
    HEAD, BRANCH
}