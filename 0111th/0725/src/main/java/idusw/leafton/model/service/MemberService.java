package idusw.leafton.model.service;

import idusw.leafton.model.DTO.MemberDTO;
import idusw.leafton.model.entity.Member;

public interface MemberService {
    //로그인 성공 여부를 반환하는 메서드
    public MemberDTO loginCheck(MemberDTO memberDTO);

    //Member 객체를 받아 회원가입 처리를 지시하는 메서드
    public void register(MemberDTO memberDTO);

    MemberDTO getMemberById(Long memberId);
}
