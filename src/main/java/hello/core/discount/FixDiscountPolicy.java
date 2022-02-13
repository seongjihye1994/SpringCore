package hello.core.discount;

import hello.core.member.Grade;
import hello.core.member.Member;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
// @Qualifier("fixDiscountPolicy")
public class FixDiscountPolicy implements DiscountPolicy {

    // 정액 1000원 할인
    private int discountFixAmount = 1000;

    @Override
    public int discount(Member member, int price) {
        if (member.getGrade() == Grade.VIP) {
            // 등급이 VIP 이면 1000원 할인
            return discountFixAmount;
        } else {
            // 등급이 VIP가 아니면 할인 x
            return 0;
        }
    }
}
