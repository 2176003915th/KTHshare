package idusw.leafton.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import idusw.leafton.model.DTO.MemberDTO;
import idusw.leafton.model.entity.Member;
import idusw.leafton.model.repository.MemberRepository;
import idusw.leafton.model.service.MemberService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;


import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

@Controller
@RequiredArgsConstructor
public class UnityController {

    //private final MemberRepository memberRepository;
    private final MemberService memberService;

    MemberDTO memberDTO;

    @GetMapping(value = "/unity")
    public String goIndex(HttpServletRequest request)
    {
        HttpSession session = request.getSession();

        memberDTO = (MemberDTO)session.getAttribute("memberDTO");
        if(memberDTO != null)
        {
            //ModelAndView mav = new ModelAndView("unity/index");
            //mav.addObject("memberDTO",memberDTO);
            return "/unity/index";
        }
        else
        {
            return null;
        }
    }

    //httpservlet 사용 시 타임리프때매 문제생기고 테스트 하기에 있어 제약이 많음(유니티 에디터 내에서 테스ㅡㅌ 안됨)
    @PostMapping(value = "/unity", consumes = "application/json")
    public @ResponseBody String handleUnityPostRequest(@RequestBody String jsonData) {

        String decodeResult = URLDecoder.decode(jsonData, StandardCharsets.UTF_8);
        System.out.println("Received JSON data: " + decodeResult);

        if(decodeResult.equals("load"))
        {
            System.out.println(memberDTO.getUnity_data());

            return memberDTO.getUnity_data();
        }
        else
        {
            if(memberDTO.getUnity_data() == null)
            {
                memberDTO.setUnity_data(decodeResult);
                System.out.println("controller"+memberDTO.getUnity_data());
                memberService.saveData(memberDTO);
            }
            else
            {
                memberDTO.setUnity_data(decodeResult);
                memberService.saveData(memberDTO);
            }
            return "success recieve data";
        }
    }
}