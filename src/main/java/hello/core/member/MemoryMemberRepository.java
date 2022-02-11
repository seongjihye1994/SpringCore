package hello.core.member;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

// 현재 설정 정보 파일인 AutoAppConfig(@Configuration 붙음)이 @Component
// 어노테이션이 붙은 클래스를 찾아서 자동으로 스프링 컨테이너에 스프링 빈으로 등록함
@Component // memoryMemberRepository 스프링빈 등록
public class MemoryMemberRepository implements MemberRepository {

    // 회원 저장소
    private static Map<Long, Member> store = new HashMap<>();
    
    // 회원 정보 저장
    @Override
    public void save(Member member) {
        store.put(member.getId(), member);
    }

    // 회원 정보 조회
    @Override
    public Member findById(Long memberId) {
        return store.get(memberId);
    }
}
