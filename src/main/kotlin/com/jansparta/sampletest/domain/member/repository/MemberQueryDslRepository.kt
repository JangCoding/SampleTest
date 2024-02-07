package com.jansparta.sampletest.domain.member.repository

import com.jansparta.sampletest.domain.member.Member
import com.jansparta.sampletest.domain.member.type.MemberSearchType
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

interface MemberQueryDslRepository {
    fun search(searchType: MemberSearchType, keyword: String, pageable: Pageable): Page<Member>
}
