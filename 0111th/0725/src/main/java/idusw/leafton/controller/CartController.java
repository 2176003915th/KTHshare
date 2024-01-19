package idusw.leafton.controller;

import idusw.leafton.model.DTO.CartDTO;
import idusw.leafton.model.DTO.MemberDTO;
import idusw.leafton.model.DTO.ProductDTO;
import idusw.leafton.model.entity.CartItem;
import idusw.leafton.model.service.CartService;
import idusw.leafton.model.service.MemberService;
import idusw.leafton.model.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@RequestMapping(value = "/pay")
@RequiredArgsConstructor
@Controller
public class CartController {

    private final MemberService memberService;
    private final ProductService productService;
    private final CartService cartService;

    /*@GetMapping(value = "/cart")
    public String goCart(){ return "pay/cart"; } */

    @GetMapping(value = "/buy")
    public String goBuy(){ return "pay/buy"; }

    @GetMapping(value = "/complete")
    public String goComplete(){ return "pay/complete"; }

    @GetMapping(value = "/cart/{memberId}")
    public String userCartPage(@PathVariable("memberId") Long memberId, Model model) {
        MemberDTO member = memberService.getMemberById(memberId);
        if(member != null) {
            //로그인 되어 있는 사용자의 장바구니 가져오기
            CartDTO userCart = cartService.getCart(memberId);

            //장바구니에 들어있는 상품 모두 가져오기
            List<CartItem> cartItemList = cartService.allUserCartView(userCart);

            //장바구니에 들어있는 상뭎들의 총 가격 -> 주문에서 처리해야 할 듯
            int totalPrice = 0;
            int totalCount = 0;
            for (CartItem cartItem : cartItemList) {
                totalPrice += cartItem.getCount() * cartItem.getProduct().getPrice();
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

    @PostMapping(value = "/cart/{memberId}/{productId}")
    public String addCartItem(@PathVariable("memberId") Long memberId, @PathVariable("productId") Long productId, int count) {

        MemberDTO member = memberService.getMemberById(memberId);
        ProductDTO product = productService.getProductById(productId);

        cartService.addCart(member, product, count);

        return "redirect:/product/product/{productId}";
    }

   /* @GetMapping("/cart/{memberId}/{cartItemId}/delete")
    public String deleteCartItem(@PathVariable("memberId") Long memberId, @PathVariable("cartItemId") Long cartItemId) {
        //로그인 한 사용자와 장바구니 사용자의 id가 일치하는지
        MemberDTO member = memberService.getMemberById(memberId);
        if(member != null) {

        }

    } */

}


