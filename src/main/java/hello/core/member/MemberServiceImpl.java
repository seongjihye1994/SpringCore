package hello.core.member;

public class MemberServiceImpl implements MemberService{

    // private final MemberRepository memberRepository = new memberRepository();
    private final MemberRepository memberRepository;

    // 기존의 직접 new memberRepository()로 인터페이스를 직접 할당받았던 코드를 지우고
    // 생성자를 통해 memberRepository에 어떤 값이 들어갈지를 결정.
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
