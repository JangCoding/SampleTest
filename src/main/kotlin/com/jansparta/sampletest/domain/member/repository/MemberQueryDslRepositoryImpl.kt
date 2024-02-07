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

class MemberQueryDslRepositoryImpl(
    private val jpaQueryFactory: JPAQueryFactory
) : MemberQueryDslRepository {

    private val member = QMember.member

    override fun search(searchType: MemberSearchType, keyword: String, pageable: Pageable): Page<Member> {
        return byPaging(pageable, member) {
            jpaQueryFactory.selectFrom(member)
                .where(
                    when (searchType) {
                        MemberSearchType.NONE -> null
                        MemberSearchType.EMAIL -> member.email.like("%$keyword%")
                        MemberSearchType.NICKNAME -> member.nickname.like("%$keyword%")
                    }
                )
            }
        }
    }

fun <T> byPaging(pageable: Pageable, path: EntityPathBase<T>, baseQueryFunc: () -> JPAQuery<T>): Page<T> {
    val baseQuery = baseQueryFunc()
    val totalCount = baseQuery
        .select(path.count())
        .fetchOne() ?: 0L
    val result = baseQuery
        .select(path)
        .offset(pageable.offset)
        .limit(pageable.pageSize.toLong())
        .fetch()
    return PageImpl(result, pageable, totalCount)
}