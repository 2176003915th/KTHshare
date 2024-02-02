package idusw.leafton.model.service;

import idusw.leafton.model.DTO.MemberDTO;
import idusw.leafton.model.entity.Member;
import idusw.leafton.model.repository.MemberRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class MemberServiceImpl implements MemberService{
    private final MemberRepository memberRepository;
    @Override
    public MemberDTO loginCheck(MemberDTO memberDTO) {
        //매개변수로 받은 DTO 객체에 있는 email과 password를 통해 조회 후 null과 상관없이 반환
        Optional<Member> opMember = memberRepository.findByEmailAndPassword(memberDTO.getEmail(), memberDTO.getPassword());
        if(opMember.isPresent()) { //null이 아닐 경우
            Member member = opMember.get(); //optional binding
            return MemberDTO.toMemberDTO(member); //DTO에 entity 데이터 주입 후 반환
        } else return null; //null일 경우
    }

    @Override
    public MemberDTO emailCheck(String email){
        Optional<Member> opMember = memberRepository.findByEmail(email);
        if(opMember.isPresent()) {
            Member member = opMember.get();
            return MemberDTO.toMemberDTO(member);
        } else return null;
    }

    @Override
    public MemberDTO save(MemberDTO memberDTO) {
        Member member = Member.toMemberEntity(memberDTO); //relation에 넣기 위해 entity에 DTO 데이터 주입
        Member result = memberRepository.save(member); //insert

        return MemberDTO.toMemberDTO(result);
    }

    @Override
    public void saveData(MemberDTO memberDTO) {
        Member member = Member.toMemberEntity(memberDTO);
        memberRepository.save(member);
    }

    @Override
    public void withdraw(Long memberId) {
        memberRepository.deleteById(memberId);
    }

    @Override
    public MemberDTO getMemberById(Long memberId) {
        Optional<Member> opMember = memberRepository.findById(memberId);
        if(opMember.isPresent()) {
            return MemberDTO.toMemberDTO(opMember.get());
        } else return null;
    }

    @Override
    public void update(MemberDTO memberDTO) {

    }
}