package idusw.leafton.model.DTO;

import idusw.leafton.model.entity.CartItem;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class CartItemDTO {
    private Long cartItemId;

    private CartDTO cartDTO;

    private ProductDTO productDTO;

    private int count;

    public static CartItemDTO toCartItemDTO(CartItem cartItem){
        CartItemDTO cartItemDTO = new CartItemDTO();
        cartItemDTO.setCartItemId(cartItem.getCartItemId());
        cartItemDTO.setCartDTO(CartDTO.toCartDTO(cartItem.getCart()));
        cartItemDTO.setProductDTO(ProductDTO.toProductDTO(cartItem.getProduct()));
        cartItemDTO.setCount(cartItem.getCount());

        return cartItemDTO;
    }
}