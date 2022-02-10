package hello.core.singleton;

import hello.core.AppConfig;
import hello.core.member.MemberService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import static org.assertj.core.api.Assertions.assertThat;

public class SingletonTest {

    @Test
    @DisplayName("스프링 없는 순수한 DI 컨테이너")
     void pureContainer() {

        // 스프링 없는 순수한 DI 컨테이너 생성성
        AppConfig appConfig = new AppConfig();

        // 1. 조회: 호출할 때 마다 객체를 생성
        MemberService memberService1 = appConfig.memberService();

        // 2. 조회: 호출할 때 마다 객체를 생성
        MemberService memberService2 = appConfig.memberService();

        // 참조값이 다른 것을 확인
        System.out.println("memberService1 = " + memberService1);
        System.out.println("memberService2 = " + memberService2);
        // memberService1 = hello.core.member.MemberServiceImpl@305b7c14
        // memberService2 = hello.core.member.MemberServiceImpl@6913c1fb
        // 객체 참조값이 서로 다른 것을 확인할 수 있다.
        // 이렇게 되면, JVM 메모리에 계속 다른 객체들이 생겨나고 올라간다.
        // 스프링은 주로 웹 어플리케이션에 특화되어 만들어 진 것인데,
        // 이 웹 어플리케이션은 고객의 요청이 무수히 많은 서비스이다.
        // 만약 대규모 서비스라면, 초당 엄청나게 많은 객체들이 메모리에 생성된다.

        // memberService1 !== memberService2
        assertThat(memberService1).isNotSameAs(memberService2);

        // 즉, 고객이 요청(호출)할 때 마다 서로 다른 객체가 계속 생성된다. -> 메모리 소모가 크다!

    }

    @Test
    @DisplayName("싱글톤 패턴을 적용한 객체 사용")
    void singletonServiceTest() {
        // new SingletonService(); // SingletonService 객체 생성 시 private access 때문에 컴파일 오류 발생!

        // getInstance()로 같은 SingletonService 객체 불러오기
        SingletonService singletonService1 = SingletonService.getInstance();
        SingletonService singletonService2 = SingletonService.getInstance();

        // 참조값이 같은 것을 확인! -> 같은 객체를 공유하여 사용
        System.out.println("singletonService1 = " + singletonService1);
        System.out.println("singletonService2 = " + singletonService2);

        // singletonService1 == singletonService2
        assertThat(singletonService1).isSameAs(singletonService2);
        // same은 ==과 같다. 즉 객체 자체가 같은지 비교함(주소값이 같은지 비교)
        // equal은 equals 비교와 같다. 즉 객체의 주소값을 비교하는 것이 아닌 그저 눈에 보이는 모양 그 자체가 같은지를 비교
    }

    @Test
    @DisplayName("스프링 컨테이너와 싱글톤")
    void springContainer() {

        // 스프링 컨테이너를 사용한 싱글톤
        ApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class);

        // 1. 조회
        MemberService memberService1 = ac.getBean("memberService", MemberService.class);

        // 2. 조회
        MemberService memberService2 = ac.getBean("memberService", MemberService.class);

        // 참조값이 같은 것을 확인
        System.out.println("memberService1 = " + memberService1);
        System.out.println("memberService2 = " + memberService2);

        // memberService1 == memberService2
        assertThat(memberService1).isSameAs(memberService2);

        // MemberService에 싱글톤과 관련된 코드가 단 줄도 없음애도 불구하고, 객체가 공유되는 것을 알 수 있다.


    }

}
