package com.jansparta.sampletest.api.service

import com.jansparta.sampletest.api.dto.MemberRegisterRequest
import com.jansparta.sampletest.domain.common.QueryDslConfig
import com.jansparta.sampletest.domain.member.Member
import com.jansparta.sampletest.domain.member.repository.MemberRepository
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.context.annotation.Import
import org.springframework.test.context.ActiveProfiles

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import(value = [QueryDslConfig::class])
@ActiveProfiles("test")
class MemberServiceTest @Autowired constructor(
    private val memberRepository: MemberRepository
) {

    // 이와같이 테스트에 사용할 객체를 생성자로 만들어 사용하면된다!!
    // Spring Boot 없는 환경에서도 생성자를 통해 의존성을 자연스럽게 주입해줄 수 있기 때문에
    // 필드 주입방식보다 생성자 주입방식이 테스트 코드 작성에 용이하다고 할 수 있다.
    private val memberService = MemberService(memberRepository)

    @Test
    fun `이미 회원가입되어있는 이메일이라면 예외가 발생하는지 확인`() {
        // GIVEN
        val 기존_회원 = Member(email = "slolee@naver.com", password = "1234", nickname = "박찬준")
        memberRepository.saveAndFlush(기존_회원)
        val req = MemberRegisterRequest(email = "slolee@naver.com", password = "4321", nickname = "WIZ")

        // WHEN & THEN
        shouldThrow<RuntimeException> { // 에러 검증 방법
            memberService.register(req)
        }.let {
            it.message shouldBe "이미 존재하는 이메일입니다."
        }

        // 추가로 데이터베이스에 저장된 값까지 검증할 수 있다.
        memberRepository.findAll()
            .filter { it.email == "slolee@naver.com" }
            .let {
                it.size shouldBe 1
                it[0].nickname shouldBe "박찬준"
            }
    }

    @Test
    fun `정상적으로 회원가입되는 시나리오 확인`() {
        // GIVEN
        val req = MemberRegisterRequest(email = "slolee@naver.com", password = "4321", nickname = "WIZ")

        // WHEN
        val result = memberService.register(req)

        // THEN
        result.email shouldBe "slolee@naver.com"
        result.nickname shouldBe "WIZ"
        memberRepository.findAll()
            .filter { it.email == "slolee@naver.com" }
            .let {
                it.size shouldBe 1L
                it[0].nickname shouldBe "WIZ"
            }
    }

}