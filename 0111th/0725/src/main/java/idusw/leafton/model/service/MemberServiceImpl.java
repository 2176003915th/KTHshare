package idusw.leafton.model.service;

import idusw.leafton.model.DTO.MemberDTO;
import idusw.leafton.model.entity.Member;
import idusw.leafton.model.repository.MemberRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class MemberServiceImpl implements MemberService{
    private final MemberRepository memberRepository;
    @Override
    public MemberDTO loginCheck(MemberDTO memberDTO) {
        Optional<Member> opMember = memberRepository.findByEmailAndPassword(memberDTO.getEmail(), memberDTO.getPassword());
        if(opMember.isPresent()) {
            Member member = opMember.get();
            return MemberDTO.toMemberDTO(member);
        } else
        {
            return null;
        }
    }

    @Override
    public void register(MemberDTO memberDTO) {
        List<Member> m = memberRepository.findAll();
        Member m1 = m.get(0);
        System.out.println(m1.getEmail());
    }

    @Override
    public MemberDTO getMemberById(Long memberId) {
        Optional<Member> member = memberRepository.findById(memberId);
        if(member.isPresent()) {
            return MemberDTO.toMemberDTO(member.get());
        }else {
            return null;
        }
    }
}
