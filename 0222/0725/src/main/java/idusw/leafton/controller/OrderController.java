package idusw.leafton.controller;

import idusw.leafton.model.DTO.*;

import idusw.leafton.model.service.CartService;
import idusw.leafton.model.service.MemberService;
import idusw.leafton.model.service.OrderService;

import idusw.leafton.model.service.ProductService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.format.DateTimeFormatter;
import java.util.*;

@RequestMapping(value = "/pay")
@RequiredArgsConstructor
@Controller
public class OrderController {

    private final MemberService memberService;
    private final CartService cartService;
    private final OrderService orderService;

    // 주문 페이지로 이동
    @GetMapping(value = "/buy/{memberId}")
    public String goBuy(@PathVariable("memberId") Long memberId, @RequestParam("checkedItems") String checkedItems, Model model, HttpSession session){
        MemberDTO member = memberService.getMemberById(memberId);
        if(member != null) {

            String[] itemIds = checkedItems.split(",");

            session.setAttribute("itemIds", itemIds); // 세션에 itemIds 저장 -> 다음 요청에서도 사용해야해서

            int totalPrice = 0;
            Map<ProductDTO, String> productEvents = new HashMap<>(); // Map을 사용하여 상품을 키로, 이벤트를 값으로 저장

            for(String itemId : itemIds) {
                CartItemDTO cartItemById = cartService.findCartItemById(Long.parseLong(itemId));
                totalPrice += cartItemById.getCount() * cartItemById.getProductDTO().getPrice() * (1 - cartItemById.getProductDTO().getSalePercentage()/100.0);

                // 해당 상품의 이벤트가 없을 경우 예외 처리
                if (cartItemById.getProductDTO().getEventDTO() != null) {
                    productEvents.put(cartItemById.getProductDTO(), cartItemById.getProductDTO().getEventDTO().getContent());
                } else {
                    productEvents.put(cartItemById.getProductDTO(), "이벤트 없음");
                }
            }

            model.addAttribute("totalPrice", totalPrice);
            model.addAttribute("productEvents", productEvents); // 상품과 진행중인 이벤트 내용을 함께 나타낼 수 있음

            return "pay/buy";
        }else {
            return "redirect:/main/index";
        }
    }

    // 주문 처리
    @PostMapping(value = "/order/{memberId}")
    public String orderCheckout(@PathVariable("memberId") Long memberId, @ModelAttribute OrderDTO orderDTO, HttpSession session, Model model){
        MemberDTO member = memberService.getMemberById(memberId);
        if(member != null) {
            //goBuy()메서드에서 저장한 itemIds 세션 정보 가져오기
            String[] itemIds = (String[]) session.getAttribute("itemIds");

            //itemIds 세션 정보를 이용해 체크한 상품의 총 가격을 계산 -> order Entity에 저장해야함
            // 총 주문 상품 금액 계산 및 주문한 상품
            int totalPrice = 0;
            for(String itemId : itemIds) {
                CartItemDTO cartItemById = cartService.findCartItemById(Long.parseLong(itemId));
                if(cartItemById.getProductDTO().getAmount() == 0 || cartItemById.getProductDTO().getAmount() < cartItemById.getCount()){
                    // 주문한 상품의 재고가 없거나 재고보다 주문한 상품의 수량이 더 많으면 메인페이지로 이동
                    return "redirect:/main/index";
                }
                totalPrice += cartItemById.getCount() * cartItemById.getProductDTO().getPrice()
                        * (1 - cartItemById.getProductDTO().getSalePercentage()/100.0);
            }
            // 배송 관련
            String deliveryCompany = "CJ대한통운";
            int deliveryFee = orderService.calculateDeliveryFee(totalPrice);

            // 주문 정보를 orderDTO에 저장
            orderDTO.setMemberDTO(member);
            orderDTO.setOrderPrice(totalPrice);
            orderDTO.setDeliveryFee(deliveryFee);
            orderDTO.setDeliveryCompany(deliveryCompany);

            // Order 객체를 DB에 저장하고, 저장된 Order 객체를 반환받음 -> 이렇게 따로 반환받지 않으면 orderItem을 생성할 때 문제 발생
            OrderDTO savedOrderDTO = orderService.addOrder(orderDTO);

            //주문한 상품을 담을 리스트 생성
            List<OrderItemDTO> orderItemList = new ArrayList<>();

            for(String itemId : itemIds){
                // 장바구니에서 선택한 CartItemId를 찾아오기
                CartItemDTO cartItemById = cartService.findCartItemById(Long.parseLong(itemId));

                // 주문한 상품의 수량만큼 상품 재고 뺴기
                orderService.decreaseStock(cartItemById.getProductDTO(), cartItemById.getCount());

                // 찾은 정보로 OrderItem을 생성
                OrderItemDTO orderItem = orderService.addOrderItem(savedOrderDTO, cartItemById.getProductDTO(), cartItemById.getCount());

                // 생성한 OrderItem을 리스트에 담기
                orderItemList.add(orderItem);

                // OrderItem에 담은 CartItem은 더 이상 필요가 없으므로 삭제 처리
                cartService.cartItemDelete(cartItemById.getCartItemId());
            }

            // 주문 번호(yyyyMMdd + 주문Id) 처리를 위해 yyyy-mm-dd 패턴을 yyyyMMdd 형식으로 변경
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
            String formattedDate = savedOrderDTO.getOrderDate().format(formatter);

            model.addAttribute("totalPrice", totalPrice);
            model.addAttribute("order", savedOrderDTO);
            model.addAttribute("orderItems", orderItemList);
            model.addAttribute("formattedDate", formattedDate);

            return "/pay/complete";
        }else{
            return "redirect:/main/index";
        }
    }

    // 바로 구매하기 버튼 클릭시 주문 페이지로 이동 처리
    @GetMapping(value = "/buy/one")
    public String goBuyOne(@RequestParam("memberId") Long memberId, @RequestParam("cartOneItemId") Long cartOneItemId, Model model, HttpSession session){
        MemberDTO member = memberService.getMemberById(memberId);

        if(member != null) {
            CartItemDTO cartOneItemById = cartService.findCartItemById(cartOneItemId);

            if(cartOneItemById.getProductDTO().getAmount() == 0 || cartOneItemById.getProductDTO().getAmount() < cartOneItemById.getCount()){
                return "redirect:/main/index";
            }

            // 상품 총합 계산 totalPrice 자료형 고민
            int totalPrice = 0;
            totalPrice = cartOneItemById.getCount() * cartOneItemById.getProductDTO().getPrice()
                    * (1 - cartOneItemById.getProductDTO().getSalePercentage()/100);

            // 이벤트 상품 처리
            Map<ProductDTO, String> productEvents = new HashMap<>(); // Map을 사용하여 상품을 키로, 이벤트를 값으로 저장
            if(cartOneItemById.getProductDTO().getEventDTO() != null){
                productEvents.put(cartOneItemById.getProductDTO(), cartOneItemById.getProductDTO().getEventDTO().getContent());
            }else{
                productEvents.put(cartOneItemById.getProductDTO(), "이벤트 없음");
            }

            // 주문 처리를 위해 cartItemId를 세션에 담아서 oderCheckOut()에서 처리할 수 있도록 넘겨줌
            // oderCheckOut()에서 String[]에 CartItemId들을 담아서 처리하므로 먼저 Long -> String -> String[] 순으로 변환
            session.setAttribute("itemIds", new String[] { String.valueOf(cartOneItemId) });


            model.addAttribute("totalPrice", totalPrice);
            model.addAttribute("productEvents", productEvents); // 상품과 진행중인 이벤트 내용을 함께 나타낼 수 있음
        }
        return "/pay/buy";
    }


}
