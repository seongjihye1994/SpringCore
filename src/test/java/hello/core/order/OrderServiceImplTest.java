package hello.core.order;

import hello.core.discount.FixDiscountPolicy;
import hello.core.member.Grade;
import hello.core.member.Member;
import hello.core.member.MemoryMemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class OrderServiceImplTest {

    @Test
    void createOrder() {

        // 테스트를 위해 임의의 회원 정보를 넣어줌
        // 스프링 없이 순수한 자바 코드를 테스트 하기 위해 사용
        // 테스트 코드 위에서 필요한 구현체들을 직접 생성해서 테스트를 위해 사용하는 것 -> 생성자 의존성 주입에서만 사용 가능
        MemoryMemberRepository memoryMemberRepository = new MemoryMemberRepository();
        memoryMemberRepository.save(new Member(1L, "name", Grade.VIP));

        OrderServiceImpl orderService = new OrderServiceImpl(new MemoryMemberRepository(), new FixDiscountPolicy());

        Order order = orderService.createOrder(1L, "itemA", 10000);
        // OrderServiceImpl 에서 의존성 주입을 setter로 할 경우 nullPointerException 발생
        // 왜? 아직 의존 빈이 만들어 지지 않았기 때문
        // 수정자 주입과 필드 주입은 생성자로 객체를 먼저 만든 다음 의존 빈을 생성한다.
        // Test 시에는 의존 빈이 만들어 지지 않았기 때문에 nullPointerException 발생

        // OrderServiceImpl 에서 의존성 주입을 생성자로 할 경우 컴파일 오류가 발생
        // 세상에서 가장 좋은 오류 컴파일 오류!

        assertThat(order.getDiscountPrice()).isEqualTo(1000);


    }

}