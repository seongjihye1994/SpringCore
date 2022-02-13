package hello.core.autowired;

import hello.core.AutoAppConfig;
import hello.core.discount.DiscountPolicy;
import hello.core.member.Grade;
import hello.core.member.Member;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.*;

public class AllBeanTest {

    @Test
    void findAllBean() {
        // AnnotationConfigApplicationContext 스프링 컨테이너 생성
        ApplicationContext ac = new AnnotationConfigApplicationContext(AutoAppConfig.class, DiscountService.class);
        // 파라미터로 넘긴 AutoAppConfig와 DiscountService 를 스프링 빈으로 등록

        DiscountService discountService = ac.getBean(DiscountService.class);
        // DiscountService 테스트를 위해 멤버 생성
        Member member = new Member(1L, "userA", Grade.VIP);
        // 정액 할인 테스트: 얼마나 할인되는지 확인
        int discountPrice = discountService.discount(member, 10000, "fixDiscountPolicy");

        assertThat(discountService).isInstanceOf(DiscountService.class);
        assertThat(discountPrice).isEqualTo(1000); // 10000 주문 시 1000원 할인 true?

        // 정률 할인 테스트: 얼마나 할인되는지 확인
        int rateDiscountPrice = discountService.discount(member, 20000, "rateDiscountPolicy");

        assertThat(rateDiscountPrice).isEqualTo(2000); // 20000 주문 시 2000원 할인 true?
    }

    // 기존 DiscountService 를 건들이지 않고 임의로 만들어 사용
    static class DiscountService {
        // 만약 클라이언트가 정율 할인과 정액 할인 둘 중에 선택할 수 있는 서비스라면?
        // 어떤 의존성이 주입될 지 모르는 상황!
        // 이런 경우 아래처럼 Map에 정율 할인과 정액 할인을 모두 넣어넣고
        // 이 Map을 생성자의 파라미티로 넘긴다.
        private final Map<String, DiscountPolicy> policyMap;
        private final List<DiscountPolicy> policies;
        // 여기서 List는 사용 가능함을 보여주기 위한 예시일 뿐임

        @Autowired
        public DiscountService(Map<String, DiscountPolicy> policyMap, List<DiscountPolicy> policies) {
            this.policyMap = policyMap;
            this.policies = policies;
            System.out.println("policyMap = " + policyMap);
            System.out.println("policies = " + policies);
        }
        // 생성자 주입으로 파라미터인 policyMap과 policies가 먼저 스프링 빈 객체로 등록되고,
        // 이후에 생성자 메소드가 호출됨.

        public int discount(Member member, int price, String discountCode) {
            // 고객이 선택한 discount 정책(discountCode)를 빈 이름과 매칭 시키기
            DiscountPolicy discountPolicy = policyMap.get(discountCode);
            // 필드에 선언된 policyMap 중 고객이 선택한 빈을 찾아와 해당 할인 정책을 리턴함.
            return discountPolicy.discount(member, price);
        }
    }
}
