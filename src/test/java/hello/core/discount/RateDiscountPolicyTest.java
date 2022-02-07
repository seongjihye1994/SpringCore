package hello.core.discount;

import hello.core.member.Grade;
import hello.core.member.Member;
import org.assertj.core.api.Assertions;
import static org.assertj.core.api.Assertions.*; // java static import
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RateDiscountPolicyTest {

    RateDiscountPolicy discountPolicy = new RateDiscountPolicy();

    // 성공 테스트
    @Test
    @DisplayName("VIP는 10% 할인이 적용되어야 한다") // @DisplayName은 일종의 주석과 유사
    void vip_o() {
        // given
        // 한 명의 vip 고객이
        Member member = new Member(1L, "memberVIP", Grade.VIP);

        // when
        // 10000원을 주문하면
        int discount = discountPolicy.discount(member, 10000);

        // then
        // 그의 10% 금액인 1000원이 할인되는 것이 맞는가?
        assertThat(discount).isEqualTo(1000);
    }

    // 실패 테스트
    @Test
    @DisplayName("VIP가 아니면 할인이 적용되지 않아야 한다")
    void vip_x() {
        // given
        // 한 명의 일반 고객이
        Member member = new Member(2L, "memberBASIC", Grade.BASIC);

        // when
        // 10000원을 주문하면
        int discount = discountPolicy.discount(member, 10000);

        // then
        // 할인금액이 없어야 함, 즉 0원이 되어야 함.
        assertThat(discount).isEqualTo(0);

/*
        만약 then의 isEqualTo(1000) 을 넣으면?

            org.opentest4j.AssertionFailedError:
            expected: 1000
            but was: 0
            Expected :1000
            Actual   :0

        우리가 기대한 건 1000 이겠지만
        사실은 0이다. -> test에서 error를 뱉음!
*/
    }

}