package hello.core.lifecycle;

import org.junit.jupiter.api.Test;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

public class BeanLifeCycleTest {

    @Test
    public void lifeCycleTest() {
        // 스프링 컨테이너 생성 + LifeCycleConfig 스프링 빈 등록
        ConfigurableApplicationContext ac = new AnnotationConfigApplicationContext(LifeCycleConfig.class);

        NetworkClient client = ac.getBean(NetworkClient.class);
        ac.close(); // close는 기본 ApplicationContext 가 제공하지 않기 때문에 ConfigurableApplicationContext 다형성 적용
    }

    // 스프링 설정 파일
    @Configuration
    static class LifeCycleConfig {
        // 수동 빈 등록
        @Bean/*(initMethod = "init", destroyMethod = "close")*/ // 빈 등록 초기화, 소멸 메소드 직접 지정
        public NetworkClient networkClient() {
            NetworkClient networkClient = new NetworkClient();// -> 수동으로 직접 new 로 생성
            networkClient.setUrl("http://hello-spring.dev");
            return networkClient;
        }
    }
}
