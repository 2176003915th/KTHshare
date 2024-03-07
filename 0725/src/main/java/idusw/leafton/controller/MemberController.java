package idusw.leafton.controller;

import idusw.leafton.model.DTO.*;
import idusw.leafton.model.service.*;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Controller
public class MemberController {
    @Autowired
    MemberService memberService;
    @Autowired
    StyleService styleService;
    @Autowired
    CartService cartService;
    @Autowired
    OrderService orderService;
    @Autowired
    EventService eventService;
    @Autowired
    MainCategoryService mainCategoryService;

    //로그인 페이지로 이동
    @GetMapping(value = "/member/login")
    private String goLogin(HttpServletRequest request) {
        request.setAttribute("type", request.getParameter("type"));
        request.setAttribute("styleList", styleService.getAll());
        return "/member/login";
    }
    //마이 페이지로 이동
    @GetMapping(value="/member/info")
    private String goMyPage(HttpServletRequest request, @RequestParam(required = false, defaultValue = "0") int page,
                            @RequestParam(required = false, defaultValue = "3") int size, @RequestParam String type,
                            HttpSession session, Model model) {
        request.setAttribute("type", type);
        if(type.equals("changeSt")) {
            request.setAttribute("styleList", styleService.getAll());
        }
        if(type.equals("orderlist")){
            MemberDTO member = (MemberDTO) session.getAttribute("memberDTO");
            // 페이지 번호, 페이지 크기, 정렬 방식을 지정하여 PageRequest 객체를 생성
            Pageable pageable = PageRequest.of(page, size, Sort.by("orderDate").descending());

            Page<OrderDTO> memberOrdersPage = orderService.findMemberOrder(member.getMemberId(), pageable);

            Map<OrderDTO, Map<String, Object>> ordersMap = new LinkedHashMap<>();

            for(OrderDTO order : memberOrdersPage.getContent()){
                List<OrderItemDTO> orderItems = orderService.allUserOrderView(order);
                int totalPrice = 0;

                for (OrderItemDTO orderItem : orderItems) {
                    totalPrice += orderItem.getCount() * orderItem.getProductDTO().getPrice() * (1 - orderItem.getProductDTO().getSalePercentage()/100.0);
                }
                int deliveryFee = orderService.calculateDeliveryFee(totalPrice);

                Map<String, Object> orderInfo = new HashMap<>();
                orderInfo.put("orderItems", orderItems);
                orderInfo.put("totalPrice", totalPrice);
                orderInfo.put("deliveryFee", deliveryFee);

                ordersMap.put(order, orderInfo);
            }

            String message = (String) model.getAttribute("message");
            if (message != null) {
                model.addAttribute("message", message);
            }

            model.addAttribute("ordersMap", ordersMap);
            model.addAttribute("totalPages", memberOrdersPage.getTotalPages()); // 총 페이지 수를 뷰에 전달
            model.addAttribute("page", page); // 현재 페이지 번호를 뷰에 전달

        }
        return "/member/info";
    }
    //로그아웃 요청을 처리하는 메서드
    @GetMapping(value="/member/logout")
    private String logout(HttpServletRequest request){
        HttpSession session = request.getSession();
        session.invalidate();//세션 회수

        request.setAttribute("mainCategoryList", mainCategoryService.viewAllMainCategory());
        request.setAttribute("eventList", eventService.getAll());

        return "/main/index";
    }



    //로그인 요청을 처리하는 메서드
    @PostMapping(value="/member/login")
    private String login(@ModelAttribute MemberDTO memberDTO, HttpServletRequest request){
        //view에서 넘어온 email과 password를 이용하여 select
        MemberDTO memberResult = memberService.loginCheck(memberDTO);

        if(memberResult != null) //로그인 성공 시
        {
            HttpSession session = request.getSession(); //session 객체 생성
            session.setAttribute("memberDTO", memberResult); //session에 DTO 주입
            request.setAttribute("mainCategoryList", mainCategoryService.viewAllMainCategory());
            request.setAttribute("eventList", eventService.getAll());
            return "/main/index";
        } else { //로그인 실패 시
            //request에 message 주입 후 다시 로그인 페이지로 이동
            request.setAttribute("message", "아이디나 비밀번호가 일치하지 않습니다");
            request.setAttribute("styleList", styleService.getAll());
            return "/member/login";
        }
    }

    //회원가입을 처리하는 메서드
    @PostMapping(value="/member/register/{styleId}")
    private String register(@PathVariable("styleId") Long styleId, @ModelAttribute MemberDTO memberDTO, HttpServletRequest request){
        //이메일 중복체크 후 중복이 아닐 경우 가입 로직으로 진행
        if (memberService.emailCheck(memberDTO.getEmail()) != null) {
            request.setAttribute("message", "이미 사용 중인 이메일입니다");
            request.setAttribute("styleList", styleService.getAll());
            return "/member/login";
        } else {
            //memberDTO에 넣을 styleDTO 생성하여 pathVariable로 받은 styleId 주입
            StyleDTO styleDTO = new StyleDTO();
            styleDTO.setStyleId(styleId);
            memberDTO.setStyleDTO(styleDTO);//memberDTO에 styleDTO 주입

            //entity에 insert
            MemberDTO result = memberService.save(memberDTO);
            cartService.createCart(result);
        }
        request.setAttribute("mainCategoryList", mainCategoryService.viewAllMainCategory());
        request.setAttribute("eventList", eventService.getAll());

        return "redirect:/main/index";
    }

    //회원 정보 수정 요청을 처리하는 메서드
    @PostMapping(value="/member/edit")
    private String edit(HttpServletRequest request, @RequestParam String type, @ModelAttribute MemberDTO memberDTO){
        HttpSession session = request.getSession();
        StyleDTO styleDTO = null;
        MemberDTO result = null;

        //view에서 날라온 요청을 분리하여 처리
        switch (type) {
            case "edit":
                styleDTO = new StyleDTO();
                styleDTO.setStyleId(Long.valueOf(request.getParameter("styleId")));
                memberDTO.setStyleDTO(styleDTO);
                result = memberService.save(memberDTO);

                session.removeAttribute("memberDTO");
                session.setAttribute("memberDTO", result);

                request.setAttribute("type", "info");
                request.setAttribute("message", "회원 정보 수정이 완료되었습니다");

                return "/member/info";
            case "delete":
                CartDTO cartDTO = cartService.findMemberCart(Long.valueOf(request.getParameter("memberId")));
                cartService.deleteCart(cartDTO.getCartId());
                memberService.withdraw(Long.valueOf(request.getParameter("memberId")));

                session.removeAttribute("memberDTO");

                request.setAttribute("message", "회원 탈퇴가 완료되었습니다. 이용해 주셔서 감사합니다");
                request.setAttribute("eventList", eventService.getAll());
                request.setAttribute("mainCategoryList", mainCategoryService.viewAllMainCategory());

                return "/main/index";
            case "changePw":
                memberDTO = memberService.getMemberById(Long.valueOf(request.getParameter("memberId")));
                //사용자가 입력한 현재 비밀번호가 데이터베이스와 일치할 경우
                if(request.getParameter("oldPassword").equals(memberDTO.getPassword())) {
                    memberDTO.setPassword(request.getParameter("password"));
                    result = memberService.save(memberDTO);

                    session.removeAttribute("memberDTO");
                    session.setAttribute("memberDTO", result);

                    request.setAttribute("type", "info");
                    request.setAttribute("message", "비밀번호가 성공적으로 변경되었습니다");
                } else {
                    System.out.println(request.getParameter("oldPassword"));
                    request.setAttribute("type", "info");
                    request.setAttribute("message", "현재 비밀번호가 일치하지 않습니다");
                }
                return "/member/info";
            case "changeSt":
                memberDTO = memberService.getMemberById(Long.valueOf(request.getParameter("memberId")));
                styleDTO = new StyleDTO();
                styleDTO.setStyleId(Long.valueOf(request.getParameter("styleId")));
                memberDTO.setStyleDTO(styleDTO);
                result = memberService.save(memberDTO);

                session.removeAttribute("memberDTO");
                session.setAttribute("memberDTO", result);

                request.setAttribute("type", "info");
                request.setAttribute("message", "선호 스타일이 성공적으로 변경되었습니다");

                return "/member/info";
            default: return null;
        }
    }

    /*---------------admin controller start------------------*/

    @GetMapping(value="admin/login/login")
    public String goLogin(){
        return "admin/login/login";
    }
    @PostMapping(value="/admin/adminLogin")
    private String adminLogin(HttpServletRequest request){
        String adminId = request.getParameter("admin-id");
        String adminPw = request.getParameter("admin-pw");
        MemberDTO adminCheck = new MemberDTO();
        if("admin".equals(adminId) && "123".equals(adminPw)){
            HttpSession session = request.getSession(); //session 객체 생성
            session.setAttribute("adminCheck",adminCheck);
            return "admin/main/index";
        } else {
            request.setAttribute("message", "아이디나 비밀번호가 일치하지 않습니다");
            return "admin/login/login";
        }
    }

    @GetMapping(value="/admin/logout")
    private String adminLogout(HttpServletRequest request){
        HttpSession session = request.getSession();
        session.invalidate();//세션 회수

        return "admin/login/login";
    }

    @GetMapping(value="/admin/member/list")
    public String goUserTable(HttpServletRequest request){
        List<MemberDTO> memberList = memberService.viewAllMembers();
        request.setAttribute("memberList", memberList);
        return "admin/member/list";
    }

    /*---------------admin controller end------------------*/
}