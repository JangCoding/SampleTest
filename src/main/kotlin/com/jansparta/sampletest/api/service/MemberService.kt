package com.jansparta.sampletest.api.service

import com.jansparta.sampletest.api.dto.MemberRegisterRequest
import com.jansparta.sampletest.api.dto.MemberResponse
import com.jansparta.sampletest.domain.member.Member
import com.jansparta.sampletest.domain.member.repository.MemberRepository
import org.springframework.stereotype.Service

@Service
class MemberService(
    private val memberRepository: MemberRepository
) {

    fun register(req: MemberRegisterRequest): MemberResponse {
        if (memberRepository.existsByEmail(req.email)) {
            throw RuntimeException("이미 존재하는 이메일입니다.")
        }
        val (email, password, nickname) = req
        return memberRepository.save(
            Member(email = email, password = password, nickname = nickname)
        ).let {
            MemberResponse.from(it)
        }
    }
}