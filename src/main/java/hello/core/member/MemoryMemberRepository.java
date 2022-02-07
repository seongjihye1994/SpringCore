package hello.core.member;

import java.util.HashMap;
import java.util.Map;

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
