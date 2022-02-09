package hello.core;

import hello.core.member.Grade;
import hello.core.member.Member;
import hello.core.member.MemberService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class MemberApp {

    public static void main(String[] args) {
        // AppConfig appConfig = new AppConfig();
        // MemberService memberService = appConfig.memberService();
        // MemberService memberService = new MemberServiceImpl();

        // @Configuration 설정 클래스에서 @Bean으로 등록한 메소드들을 관리해주는 스프링 컨테이너이다.
        // 이 스프링 컨테이너(ApplicationContext)를 생성해준다.
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(AppConfig.class);
        // AppConfig에 있는 설정 정보를 기반으로 @Bean으로 등록된 객체들을 스프링 컨테이너에 등록하고 관리함.
        // 기존에는 AppConfig 객체를 생성해서 appConfig.memberService 처럼 직접 찾아왔다.
        // 하지만 이 스프링 컨테이너(ApplicationContext)를 사용하면 appl    icationContext.getBean() 을 통해 찾아올 수 있다.
        MemberService memberService = applicationContext.getBean("memberService", MemberService.class);
                                                        // 파라미터 2개 (1번 파라미터: Bean 이름이고 메소드명이다. 2번 파라미터: Bean의 return 타입)

        Member member = new Member(1L, "memberA", Grade.VIP);
        memberService.join(member);

        Member findMember = memberService.findMember(1L);
        System.out.println("new member: " + member.getName());
        System.out.println("find Member: " + findMember.getName());
    }
}
