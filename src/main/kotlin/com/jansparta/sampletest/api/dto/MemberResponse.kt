package com.jansparta.sampletest.api.dto

import com.jansparta.sampletest.domain.member.Member

data class MemberResponse(
    val memberId: Long,
    val email: String,
    val nickname: String
) {

    companion object {
        fun from(member: Member) = MemberResponse(
            memberId = member.id!!,
            email = member.email,
            nickname = member.nickname
        )
    }
}
