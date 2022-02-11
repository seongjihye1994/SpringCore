package hello.core.autowired;

import hello.core.member.Member;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.lang.Nullable;

import java.util.Optional;

public class AutowiredTest {

    @Test
    void AutowiredOption() {

        ApplicationContext ac = new AnnotationConfigApplicationContext(TestBean.class);

    }

    static class TestBean {

        // 옵션이 false일 경우 자동 주입할 대상이 없으면 수정자 메서드 자체가 호출 안됨
        // 즉 noBean1라는 스프링 빈이 없기 때문에 setNoBean1 메소드는 호출조차 되지 않는다.
        @Autowired(required = false)
        public void setNoBean1(Member noBean1) {
            // 스프링 컨테이너와 관련 없는 Member 클래스를 파라미터로 전달
            System.out.println("noBean1 = " + noBean1);
        }

        // 옵션이 @Nullable 일 경우 자동 주입할 대상이 없으면 null이 입력된다.
        // 즉 noBean2라는 스프링 빈은 없지만 호출은 되기 때문에 null로 찍힌다.
        @Autowired
        public void setNoBean2(@Nullable Member noBean2) {
            System.out.println("noBean2 = " + noBean2);
        }

        // 옵션이 Optional<> 일 경우 자동 주입할 대상이 없으면 Optional.empty 가 입력된다.
        // 즉 noBean3라는 스프링 빈은 없기 때문에 Optional.empty가 찍히고, 있으면 빈이 출력된다.
        @Autowired
        public void setNoBean3(Optional<Member> noBean3) {
            System.out.println("noBean3 = " + noBean3);
        }

    }
}
