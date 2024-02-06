package com.jansparta.sampletest.domain.member

import com.jansparta.sampletest.domain.common.QueryDslConfig
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.context.annotation.Import
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
        // GIVEN

        // WHEN

        // THEN
    }

    @Test
    fun `SearchType 이 NONE 이 아닌 경우 Keyword 에 의해 검색되는지 결과 확인`() {
        // GIVEN

        // WHEN

        // THEN
    }

    @Test
    fun `Keyword 에 의해 조회된 결과가 0건일 경우 결과 확인`() {
        // GIVEN

        // WHEN

        // THEN
    }

    @Test
    fun `조회된 결과가 10개, PageSize 6일 때 0Page 결과 확인`() {
        // GIVEN

        // WHEN

        // THEN
    }

    @Test
    fun `조회된 결과가 10개, PageSize 6일 때 1Page 결과 확인`() {
        // GIVEN

        // WHEN

        // THEN
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