package com.jansparta.sampletest.api.dto

data class MemberRegisterRequest(
    val email: String,
    val password: String,
    val nickname: String
) {
}