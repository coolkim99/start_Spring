package hello.hellospring.service;

import hello.hellospring.domain.Member;
import hello.hellospring.repository.MemberRepository;
import hello.hellospring.repository.MemoryMemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

//Controller의 AutoWired로 연결시켜 주기 위해서 Service 어노테이션 사용해야함
//SpringConfig에 Bean으로 관리시 없어도 됨
//@Service

@Transactional
public class MemberService {
    private final MemberRepository memberRepository;

    //MemberRepository가 필요함
    @Autowired
    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

//  회원가입
    public Long join(Member member) {

        validateDuplicateMember(member);
        memberRepository.save(member);
        return member.getId();
    }

    private void validateDuplicateMember(Member member) {
        // Optional은 바로 반환하는 것은 좋지 않음
        // 바로 ifPresent() 써주는 것이 좋음
        memberRepository.findByName(member.getName())
                        .ifPresent(m -> {
                            throw new IllegalStateException("이미 존재하는 회원입니다.");
                        });
    }

//  전체 회원 조회
    public List<Member> findMembers() {
        return memberRepository.findAll();
    }

//  회원 조회
    public Optional<Member> findOne(Long memberId) {
        return memberRepository.findById(memberId);
    }
}
