package hello.core.order;

import hello.core.annotation.MainDiscountPolicy;
import hello.core.discount.DiscountPolicy;
import hello.core.member.Member;
import hello.core.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
// @RequiredArgsConstructor // final이 붙은 필드를 가지고 생성자를 자동으로 만들어준다!
public class OrderServiceImpl implements OrderService {

    // 구현체 생성
    private final MemberRepository memberRepository;
    // private final DiscountPolicy discountPolicy = new FixDiscountPolicy(); // 현재 고정 할인 금액으로 적용되어 있음.
    // private final DiscountPolicy discountPolicy = new RateDiscountPolicy(); // 고정 할인 금액을 정률 할인 금액으로 바꿈
    // DiscountPolicy 인터페이스 역할을 구현한 RateDiscountPolicy 정률 할인 클래스!

    private final DiscountPolicy discountPolicy;
    // 인터페이스에만 의존하도록 코드 변경!



    // 의존성 주입 시
    // 생성자 주입, 세터 주입, 필드 주입 등이 있는데

    // 생성자로 주입 시에는 생성자로 스프링 빈에 등록과 동시에 의존성 주입이 동시에 일어남

    // 세터로 등록하면 생성자에서 스프링 빈에 등록되고 세터를 통해 의존성 주입이 일어남

    // 필드 주입은 디폴트 생성자를 사용하고, 세터는 필요없고 필드에 @Autowired 지정하면 됨.



    // setter를 사용한 의존성 주입
//    @Autowired
//    public void setMemberRepository(MemberRepository memberRepository) {
//        this.memberRepository = memberRepository;
//    }
//
//    @Autowired
//    public void setDiscountPolicy(DiscountPolicy discountPolicy) {
//        this.discountPolicy = discountPolicy;
//    }

    // 생성자 new OrderServiceImpl(memberRepository, discountPolicy);
    // @Autowired // 생성자가 1개만 있을 때는 @Autowired 생략 가능
    public OrderServiceImpl(MemberRepository memberRepository, /*@Qualifier("mainDiscountPolicy")*/ @MainDiscountPolicy DiscountPolicy discountPolicy) {
        this.memberRepository = memberRepository;                                                 // 직접 만든 어노테이션 가져다 쓰기
        this.discountPolicy = discountPolicy;
    }

    @Override
    public Order createOrder(Long memberId, String itemName, int itemPrice) {
        // 1. 회원 조회
        Member member = memberRepository.findById(memberId);

        // 2. 할인 금액 적용 (member 객체는 미래의 기능 확장성에 의해 넘겨줌)
        int discountPrice = discountPolicy.discount(member, itemPrice);

        // 3. 할인 적용된 금액으로 주문 생성
        return new Order(memberId, itemName, itemPrice, discountPrice);
    }

    // 테스트 용도
    public MemberRepository getMemberRepository() {
        return memberRepository;
    }
}
