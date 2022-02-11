package hello.core;

import hello.core.member.MemberRepository;
import hello.core.member.MemoryMemberRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;

// 설정 정보 파일
@Configuration
// @Component 이 붙은 클래스를 스캔해서 자동으로 스프링 컨테이너에 스프링 빈으로 등록
@ComponentScan(
        // basePackages로 지정한 클래스부터 스캔을 시작
        // basePackages = "hello.core.member",

        // 자동으로 스프링 컨테이너에 스프링 빈으로 등록할 때 제외할 대상 설정
        excludeFilters = @ComponentScan.Filter(type = FilterType.ANNOTATION, classes = Configuration.class)
        // 지금 @Configuration 어노테이션이 붙은 클래스는 AppConfig 클래스로
        // 공부를 위해 수동으로 스프링 컨테이너에 등록해주는 설정 정보 파일이므로
        // 이 테스트에서는 AppConfig 파일을 제외한다.
)

// AppConfig에서 @Bean태그를 사용해 수동으로 스프링 빈을 등록하고 의존성을 주입해주는 것과는 달리
// AutoAppConfig는 @ComponentScan을 사용해 @Compnent가 붙은 클래스를 자동으로
// 스프링 빈으로 등록해주고, 의존성 주입은 @Autowired가 붙은 메소드를 자동으로 주입해준다.
public class AutoAppConfig {

    /*
    @Bean(name = "memoryMemberRepository") // 자동 빈 등록과 수동 빈 등록 중복 테스트를 위해 수동으로 빈 등록
    public MemberRepository memberRepository() {
        return new MemoryMemberRepository();
    }
    */

}
