package com.jansparta.sampletest.domain.member

import com.jansparta.sampletest.domain.common.QueryDslConfig
import com.jansparta.sampletest.domain.member.type.MemberSearchType
import io.kotest.matchers.should
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.context.annotation.Import
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.test.context.ActiveProfiles

@DataJpaTest // Test 를 위한 준비물들을 스캔하고 가져다줌.
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import(value = [QueryDslConfig::class])  // @DataJpaTest 외에 필요한 내용 직접 Import
@ActiveProfiles("test")
class MemberRepositoryTest @Autowired constructor(
    private val memberRepository: MemberRepository
) {

    @Test
    fun `SearchType 이 NONE 일 경우 전체 데이터 조회되는지 확인`() {
        // GIVEN : 테스트 준비물 만드는 과정
        // 준비한 샘플 데이터부터 저장
        // Flush() : Query 전송. @Transactional 로 설정되어 있기 때문에 Flush 안해주면 save 한 결과가 바로 반영되지 않고 쓰기지연 저장소에 남아있음
        memberRepository.saveAllAndFlush(DEFAULT_MEMBER_LIST)

        // WHEN : 테스트 하고자 하는 메서드 호출
        val result1 : Page<Member> = memberRepository.search(MemberSearchType.NONE, "", Pageable.ofSize(10))
        val result2 : Page<Member> = memberRepository.search(MemberSearchType.NONE, "", Pageable.ofSize(8))
        val result3 : Page<Member> = memberRepository.search(MemberSearchType.NONE, "", Pageable.ofSize(15))

        // THEN : 결과 검증
        result1.content.size shouldBe 10
        result2.content.size shouldBe 8
        result3.content.size shouldBe 10
    }

    @Test
    fun `SearchType 이 NONE 이 아닌 경우 Keyword 에 의해 검색되는지 결과 확인`() {
        // GIVEN
        memberRepository.saveAllAndFlush(DEFAULT_MEMBER_LIST)

        // WHEN
        val result1 : Page<Member> = memberRepository.search(MemberSearchType.EMAIL, "naver.com", Pageable.ofSize(10))
        // THEN
        result1.content.size shouldBe 4
    }

    @Test
    fun `Keyword 에 의해 조회된 결과가 0건일 경우 결과 확인`() {
        // GIVEN
        memberRepository.saveAllAndFlush(DEFAULT_MEMBER_LIST)

        // WHEN
        val result1 : Page<Member> = memberRepository.search(MemberSearchType.EMAIL, "kakao.com", Pageable.ofSize(10))

        // THEN
        result1.content.size shouldBe 0
    }

    @Test
    fun `조회된 결과가 10개, PageSize 6일 때 0Page 결과 확인`() {
        // GIVEN
        memberRepository.saveAllAndFlush(DEFAULT_MEMBER_LIST)

        // WHEN
        val result : Page<Member> = memberRepository.search(MemberSearchType.NICKNAME, "sample", PageRequest.of(0,6))

        // THEN
        result.content.size shouldBe 6
        result.isLast shouldBe false
        result.totalPages shouldBe 2
        result.number shouldBe 0
        result.totalElements shouldBe 10
    }

    @Test
    fun `조회된 결과가 10개, PageSize 6일 때 1Page 결과 확인`() {
        // GIVEN
        memberRepository.saveAllAndFlush(DEFAULT_MEMBER_LIST)

        // WHEN
        val result : Page<Member> = memberRepository.search(MemberSearchType.NICKNAME, "sample", PageRequest.of(1,6))

        // THEN
        result.content.size shouldBe 4
        result.isLast shouldBe true
        result.totalPages shouldBe 2
        result.number shouldBe 1
        result.totalElements shouldBe 10
    }


    // 샘플 데이터
    companion object {
        private val DEFAULT_MEMBER_LIST = listOf(
            Member(email = "sample1@naver.com", password = "aaaa", nickname = "sample1"),
            Member(email = "sample2@gmail.com", password = "aaaa", nickname = "sample2"),
            Member(email = "sample3@daum.net", password = "aaaa", nickname = "sample3"),
            Member(email = "sample4@gmail.com", password = "aaaa", nickname = "sample4"),
            Member(email = "sample5@naver.com", password = "aaaa", nickname = "sample5"),
            Member(email = "sample6@daum.net", password = "aaaa", nickname = "sample6"),
            Member(email = "sample7@naver.com", password = "aaaa", nickname = "sample7"),
            Member(email = "sample8@gmail.com", password = "aaaa", nickname = "sample8"),
            Member(email = "sample9@naver.com", password = "aaaa", nickname = "sample9"),
            Member(email = "sample10@gmail.com", password = "aaaa", nickname = "sample10")
        )
    }
}