package idusw.leafton.controller;

import idusw.leafton.model.DTO.CartDTO;
import idusw.leafton.model.DTO.CartItemDTO;
import idusw.leafton.model.DTO.MemberDTO;
import idusw.leafton.model.DTO.ProductDTO;
import idusw.leafton.model.entity.CartItem;
import idusw.leafton.model.service.CartService;
import idusw.leafton.model.service.MemberService;
import idusw.leafton.model.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping(value = "/pay")
@RequiredArgsConstructor
@Controller
public class CartController {

    private final MemberService memberService;
    private final ProductService productService;
    private final CartService cartService;

    // 해당 사용자의 장바구니에 담긴 물건을 cart.html에 나타냄
    @GetMapping(value = "/cart/{memberId}")
    public String userCartPage(@PathVariable("memberId") Long memberId, Model model) {
        MemberDTO member = memberService.getMemberById(memberId);
        if(member != null) {
            //로그인 되어 있는 사용자의 장바구니 가져오기
            CartDTO userCart = cartService.findMemberCart(memberId);

            //장바구니에 들어있는 상품 모두 가져오기
            List<CartItem> cartItemList = cartService.allUserCartView(userCart);


            //장바구니에 들어있는 상뭎들의 총 가격 -> 주문에서 처리해야 할 듯
            int totalPrice = 0; // 최종 주문 정보에 나타내기 위한 물품 총 가격
            int totalCount = 0; // 최종 주문 정보에 나타내기 위한 물품 총 개수

            for (CartItem cartItem : cartItemList) {
                totalPrice += cartItem.getCount() * cartItem.getProduct().getPrice() * (1 - cartItem.getProduct().getSalePercentage()/100.0) ;
                totalCount += cartItem.getCount();
            }

            model.addAttribute("totalCount", totalCount);
            model.addAttribute("totalPrice", totalPrice);
            model.addAttribute("cartItems", cartItemList);

            return "pay/cart";
        }else {
            return "redirect:/main/index";
        }
    }

    //장바구니에 해당하는 물건 담기
    @PostMapping(value = "/cart/{memberId}/{productId}")
    public String addCartItem(@PathVariable("memberId") Long memberId, @PathVariable("productId") Long productId, int count) {

        MemberDTO member = memberService.getMemberById(memberId);
        ProductDTO product = productService.getProductById(productId);

        cartService.addCart(member, product, count);

        return "redirect:/product/product/{productId}";
    }

    // 장바구니에서 해당하는 물건 제거
    @GetMapping("/cart/{memberId}/{cartItemId}/delete")
    public String deleteCartItem(@PathVariable("memberId") Long memberId, @PathVariable("cartItemId") Long cartItemId, Model model) {
        //로그인 한 사용자 id가 db에 존재하는지 확인
        MemberDTO member = memberService.getMemberById(memberId);
        if(member != null) {
            //넘겨받은 cartItemId로 장바구니에 담은 상품 찾기 -> 삭제하고 변화한 정보를 나타내기 위해 찾음
            CartItemDTO cartItem = cartService.findCartItemById(cartItemId);

            //해당 사용자의 카트 찾기 -> allUserCartView 메서드 실행을 위해 찾음
            CartDTO memberCart = cartService.findMemberCart(memberId);

            //장바구니 물건 삭제
            cartService.cartItemDelete(cartItemId);

            //장바구니에 들어있는 상품 모두 가져오기 -> 삭제후 장바구니에 담긴 남은 물건을 불러옴
            List<CartItem> cartItemList = cartService.allUserCartView(memberCart);


            int totalPrice = 0; // 최종 주문 정보에 나타내기 위한 물품 총 가격
            int totalCount = 0; // 최종 주문 정보에 나타내기 위한 물품 총 개수

            for (CartItem cartitem : cartItemList) {
                totalPrice += cartitem.getCount() * cartitem.getProduct().getPrice() * (1 - cartitem.getProduct().getSalePercentage()/100.0) ;
                totalCount += cartItem.getCount();
            }

            model.addAttribute("totalCount", totalCount);
            model.addAttribute("totalPrice", totalPrice);
            model.addAttribute("cartItems", cartItemList);

            return "redirect:/pay/cart/{memberId}";

        }else { //로그인 정보가 없을 경우
            return "redirect:/main/index";
        }
    }

    // 바로 구매하기 버튼 클릭시 장바구니 생성 및 구매 상품 장바구니에 담기
    // 기존에는 @PathVariable을 사용하여 변수를 받아왔지만 css가 꺠지는 오류가 발생하여 @RequestParam으로 변경
    @PostMapping(value = "/cart/one")
    public String addOneCartItem(@RequestParam("memberId") Long memberId, @RequestParam("productId") Long productId, int count) {

        MemberDTO member = memberService.getMemberById(memberId);
        ProductDTO product = productService.getProductById(productId);

        Long cartOneItemId = cartService.addOneCart(member, product, count);


        return "redirect:/pay/buy/one?memberId=" + memberId + "&cartOneItemId=" + cartOneItemId;

    }

}


