package hello.core.member;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MemberServiceImpl implements MemberService{

    // private final MemberRepository memberRepository = new memberRepository();
    private final MemberRepository memberRepository;

    // 컴포넌트 스캔을 통해 설정파일(@Configuration)이 @Component 가 붙은
    // 클래스를 모두 스캔한 후 자동으로 스프링 컨테이너에 스프링 빈으로 등로한다.
    // 하지만 의존성 주입은 어떻게 해주나?
    // 기존 AppConfig는 의존성 주입을 new로 직접 호출해서 해주고 있자만,
    // 현재 AutoAppConfig에서는 텅 빈 코드이다. -> 의존성 주입 못해줌.
    // 그래서 아래처럼 주입할 객체의 생성자에
    // @Autowired를 붙여주면 스프링이 자동으로 의존관계를 주입한다.
    @Autowired // ac.getBean(MemberRepository.class) 처럼 동작함.
    public MemberServiceImpl(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Override
    public void join(Member member) {
        memberRepository.save(member);
    }

    @Override
    public Member findMember(Long memberId) {
        return memberRepository.findById(memberId);
    }

    // 테스트 용도
    public MemberRepository getMemberRepository() {
        return memberRepository;
    }
}
