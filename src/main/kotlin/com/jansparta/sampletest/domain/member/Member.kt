package com.jansparta.sampletest.domain.member
import jakarta.persistence.*

@Entity
class Member(

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    var id: Long? = null,

    val email: String,
    val password: String,
    var nickname: String
) {
}