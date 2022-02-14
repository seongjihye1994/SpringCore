package hello.core.scope;

import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Scope;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import static org.assertj.core.api.Assertions.assertThat;

public class SingletonTest {

    @Test
    void singletonBeanFine() {
        // 스프링 컨테이너 생성과 동시에 SingletonBean을 스프링 빈으로 등록
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(SingletonBean.class);

        SingletonBean singletonBean1 = ac.getBean(SingletonBean.class);
        SingletonBean singletonBean2 = ac.getBean(SingletonBean.class);
        System.out.println("singletonBean1 = " + singletonBean1);
        System.out.println("singletonBean2 = " + singletonBean2);

        assertThat(singletonBean1).isSameAs(singletonBean2);

        ac.close(); // 스프링 컨테이너 종료

    }

    @Scope("singleton") // 디폴트가 싱글톤 -> 생략 가능
    static class SingletonBean {
        @PostConstruct
        public void init() { // 의존성 주입 후 초기화 메소드
            System.out.println("SingletonBean.init");
        }

        @PreDestroy
        public void destroy() { // 빈 소멸 전 소멸 메소드
            System.out.println("SingletonBean.destroy");
        }
    }
}
