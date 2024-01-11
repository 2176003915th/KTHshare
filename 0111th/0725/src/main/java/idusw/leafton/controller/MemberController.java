package idusw.leafton.controller;

import idusw.leafton.model.DTO.MemberDTO;
import idusw.leafton.model.entity.Member;
import idusw.leafton.model.service.MemberService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
@RequestMapping(value = "/member")
@Controller
public class MemberController {
    @Autowired
    MemberService memberService;
    @GetMapping(value = "/login")
    private String goLogin() { return "/member/login"; }

    @PostMapping(value="/login")
    private String login(@ModelAttribute MemberDTO memberDTO, HttpServletRequest request){
        MemberDTO memberResult = memberService.loginCheck(memberDTO);

        if(memberResult != null)
        {
            HttpSession session = request.getSession();
            session.setAttribute("memberDTO", memberResult);
            return "/main/index";
        } else {
            request.setAttribute("message", "아이디나 비밀번호가 일치하지 않습니다");
            return "/member/login";
        }
    }

    @GetMapping(value="/logout")
    private String logout(HttpServletRequest request){
        HttpSession session = request.getSession();
        session.invalidate();//세션 회수

        return "/main/index";
    }

    @GetMapping(value = "/register")
    private String goRegister() {
        return "/member/register";
    }

    @PostMapping(value="/register")
    private String register(@ModelAttribute Member member, HttpServletRequest request){
        return "/main/index";
    }
}
