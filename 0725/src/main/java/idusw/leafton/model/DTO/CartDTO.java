package idusw.leafton.model.DTO;

import idusw.leafton.model.entity.Cart;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class CartDTO {
    private Long cartId;
    private MemberDTO memberDTO;

    public static CartDTO toCartDTO(Cart cart){
        CartDTO cartDTO = new CartDTO();
        cartDTO.setCartId(cart.getCartId());
        cartDTO.setMemberDTO(MemberDTO.toMemberDTO(cart.getMember()));

        return cartDTO;

    }

}