package hello.core.beanfind;

import hello.core.AppConfig;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class ApplicationContextInfoTest {

    AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class);

    // 스프링 컨테이너에 등록된 모든 빈 출력
    @Test
    @DisplayName("모든 빈 출력하기")
    void findAllBean() { // JUnit5 부터는 public 생략 가능
        String[] beanDefinitionNames = ac.getBeanDefinitionNames(); // 빈 객체 이름들 꺼내서 배열에 담기

        for (String beanDefinitionName : beanDefinitionNames) { // 꺼낸 빈 객체 반복문으로 돌림
            Object bean = ac.getBean(beanDefinitionName); // 빈 출력 -> Bean은 Object 타입으로 받음
            System.out.println("name = " + beanDefinitionName + " Object = " + bean);
        }
    }

    // 내가 @Bean 으로 지정한 빈만 출력
    @Test
    @DisplayName("애플리케이션 빈 출력하기")
    void findApplicationBean() {
        String[] beanDefinitionNames = ac.getBeanDefinitionNames();

        for (String beanDefinitionName : beanDefinitionNames) {
            BeanDefinition beanDefinition = ac.getBeanDefinition(beanDefinitionName); // 빈 객체 메타정보 꺼내서 배열에 담기

            // ROLE_APPLICATION : 일반적으로 사용자가 정의한 빈
            // ROLE_INFRASTRUCTURE : 스프링이 내부에서 사용하는 빈
            if (beanDefinition.getRole() == BeanDefinition.ROLE_APPLICATION) {
                // Role에서 ROLE_APPLICATION은 내가 개발하기 위해 등록한 Bean의 역할을 의미
                Object bean = ac.getBean(beanDefinitionName);
                System.out.println("name = " + beanDefinitionName + " Object = " + bean);
            }
        }
    }
}
