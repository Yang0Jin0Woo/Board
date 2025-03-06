package item.demo.service;

import item.demo.domain.Item;
import item.demo.domain.Member;
import item.demo.repository.MemberRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class MemberService {

    private final MemberRepository memberRepository;

    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public void saveMember(Member member){
        memberRepository.save(member);
    }

    @Transactional(readOnly = true)
    public Member findMember(Long id){
        return memberRepository.findById(id);
    }

    @Transactional(readOnly = true)
    public List<Member> findListMember(){
        return memberRepository.findAll();
    }

    public void updateMember(Member updatedMember, Long id) {
        Member member = memberRepository.findById(id);
        if (member != null) {
            member.setName(updatedMember.getName());
            member.setEmail(updatedMember.getEmail());
            member.setPassword(updatedMember.getPassword());
        }
    }

    public void deleteMember(Long id) {
        Member member = memberRepository.findById(id);
        if(member != null) {
            memberRepository.delete(member);
        }
    }
}
