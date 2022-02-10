package hello.core.order;

import hello.core.discount.DiscountPolicy;
import hello.core.member.Member;
import hello.core.member.MemberRepository;
import hello.core.member.MemoryMemberRepository;

public class OrderServiceImpl implements OrderService {

    // 구현체 생성
    private final MemberRepository memberRepository;
    // private final DiscountPolicy discountPolicy = new FixDiscountPolicy(); // 현재 고정 할인 금액으로 적용되어 있음.
    // private final DiscountPolicy discountPolicy = new RateDiscountPolicy(); // 고정 할인 금액을 정률 할인 금액으로 바꿈
                // DiscountPolicy 인터페이스 역할을 구현한 RateDiscountPolicy 정률 할인 클래스!

    private final DiscountPolicy discountPolicy;
    // 인터페이스에만 의존하도록 코드 변경!

    // 생성자
    public OrderServiceImpl(MemberRepository memberRepository, DiscountPolicy discountPolicy) {
        this.memberRepository = memberRepository;
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
