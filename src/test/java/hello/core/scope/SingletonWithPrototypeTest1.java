package hello.core.scope;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Scope;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.inject.Provider;

import static org.assertj.core.api.Assertions.assertThat;

public class SingletonWithPrototypeTest1 {

    @Test
    void prototypeFind() {
        // 스프링 컨테이너 생성 + PrototypeBean 스프링 빈 등록
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(PrototypeBean.class);

        // -- 첫번째 prototype 객체 조회 --
        // PrototypeBean 스프링 빈 타입으로 조회
        PrototypeBean prototypeBean1 = ac.getBean(PrototypeBean.class);

        // PrototypeBean 스프링 빈의 addCount 메소드 호출 -> ++1
        prototypeBean1.addCount();

        // 결과는 1이 되어야 함.
        assertThat(prototypeBean1.getCount()).isEqualTo(1);

        // -- 두번째 prototype 객체 조회 --
        // PrototypeBean 스프링 빈 타입으로 조회
        PrototypeBean prototypeBean2 = ac.getBean(PrototypeBean.class);

        // PrototypeBean 스프링 빈의 addCount 메소드 호출 -> ++1
        prototypeBean2.addCount();

        // 결과는 1이 되어야 함.
        assertThat(prototypeBean2.getCount()).isEqualTo(1);

    }

    @Test
    void singletonClientUserPrototype() {
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(PrototypeBean.class, ClientBean.class);

        ClientBean clientBean1 = ac.getBean(ClientBean.class);
        int count1 = clientBean1.logic();
        assertThat(count1).isEqualTo(1);

        ClientBean clientBean2 = ac.getBean(ClientBean.class);
        int count2 = clientBean2.logic();
        assertThat(count2).isEqualTo(1);

    }

    @Scope("singleton")
    static class ClientBean { // 이 클래스는 스프링 컨테이너가 생성되면서 동시에 파라미터로 넘겨줬기 때문에 스프링 빈으로 등록됨
        // private final PrototypeBean prototypeBean; // 생성자로부터 리턴받은 의존성 객체 -> 생성 시점에 이미 주입됨
        // 그래서 client1이 호출한 getCount 로직과, client2가 호출한 getCount 로직의 return 값이 동일할 수 밖에

        // @Autowired
        // private ObjectProvider<PrototypeBean> prototypeBeanProvider; // 스프링 컨테이너에서 찾고 싶은 빈을 찾아주는 역할
        // ObjectFactory의 자식이 ObjectProvider

        @Autowired
        private Provider<PrototypeBean> prototypeBeanProvider; // 순수 자바 코드의 DL 객체 (스프링 의존x)

//        @Autowired
//        public ClientBean(PrototypeBean prototypeBean) { // 생성자를 통해 객체를 만듦과 동시에 PrototypeBean 의존성 주입
//            this.prototypeBeanProvider = prototypeBean;
//        }

        public int logic() {
            // PrototypeBean prototypeBean = prototypeBeanProvider.getObject();
            // 수많은 기능을 제공하는 스프링 컨테이너에서 빈을 조회하는 것 말고
            // 딱 DL 기능만 사용할 수 있는 ObjectProvider에서 빈 조회해서 사용

            PrototypeBean prototypeBean = prototypeBeanProvider.get();

            prototypeBean.addCount();
            int count = prototypeBean.getCount();
            return count;
        }
    }

    @Scope("prototype")
    static class PrototypeBean {

        // count 필드
        private int count = 0;

        // count 필드에 +1
        public void addCount() {
            count++;
        }

        // count 필드 리턴
        public int getCount() {
            return count;
        }

        // 스프링 빈 초기화 메소드
        @PostConstruct
        public void init() {
            System.out.println("PrototypeBean.init " + this); // '나'를 찍어줌 -> 참조값을 확인
        }

        // 스프링 빈 소멸 메소드 (프로토타입에서는 호출 안됨)
        @PreDestroy
        public void destroy() {
            System.out.println("PrototypeBean.destroy");
        }
    }
}
