package idusw.leafton.model.service;

import idusw.leafton.model.DTO.MemberDTO;

import java.util.List;

public interface MemberService {
    //로그인 성공 여부를 반환하는 메서드
    public MemberDTO loginCheck(MemberDTO memberDTO);

    //email 중복 여부를 확인하는 메서드
    public MemberDTO emailCheck(String email);

    //MemberDTO 객체를 받아 회원가입, 정보 수정 처리를 지시하는 메서드
    public MemberDTO save(MemberDTO memberDTO);

    //Unity json 데이터를 저장하는 메서드
    public void saveData(MemberDTO memberDTO);

    //회원 탈퇴를 처리하는 메서드
    public void withdraw(Long memberId);

    //member_id를 통해 조회하는 메서드
    public MemberDTO getMemberById(Long memberId);

    //update
    public void update(MemberDTO memberDTO);

    List<MemberDTO> viewAllMembers();
}