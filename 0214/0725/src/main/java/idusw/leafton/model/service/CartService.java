package idusw.leafton.model.service;

import idusw.leafton.model.DTO.CartDTO;
import idusw.leafton.model.DTO.CartItemDTO;
import idusw.leafton.model.DTO.MemberDTO;
import idusw.leafton.model.DTO.ProductDTO;
import idusw.leafton.model.entity.CartItem;

import java.util.List;


public interface CartService {
    void addCart(MemberDTO member, ProductDTO product, int count);

    List<CartItemDTO> allUserCartView(CartDTO userCart);

    CartItemDTO findCartItemById(Long cartItemId);

    CartDTO findMemberCart(Long memberId);

    void cartItemDelete(Long cartItemId);

    Long addOneCart(MemberDTO member, ProductDTO product, int count);

    void deleteCart(Long cartId);

    void createCart(MemberDTO result);


}
