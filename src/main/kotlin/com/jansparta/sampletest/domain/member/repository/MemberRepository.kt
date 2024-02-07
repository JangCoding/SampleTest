package com.jansparta.sampletest.domain.member.repository

import com.jansparta.sampletest.domain.member.Member
import com.jansparta.sampletest.domain.member.QMember
import com.jansparta.sampletest.domain.member.type.MemberSearchType
import com.querydsl.core.types.dsl.EntityPathBase
import com.querydsl.jpa.impl.JPAQuery
import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface MemberRepository : JpaRepository<Member, Long>, MemberQueryDslRepository {
    fun existsByEmail(email: String): Boolean
}