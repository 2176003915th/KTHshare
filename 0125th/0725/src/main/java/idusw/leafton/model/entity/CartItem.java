package idusw.leafton.model.entity;

import idusw.leafton.model.DTO.CartItemDTO;

import idusw.leafton.model.DTO.ProductDTO;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name="cartItem")
public class CartItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cartItemId;

    @ManyToOne
    @JoinColumn(name = "cartId")
    private Cart cart;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "productId")
    private Product product;

    @Column
    private int count;

    public static CartItem createCartItem(Cart cart, Product product, int count) {
        CartItem cartItem = new CartItem();
        cartItem.setCart(cart);
        cartItem.setProduct(product);
        cartItem.setCount(count);
        return cartItem;
    }

    public void addCount(int count){
        this.count = count;
    }

    public static CartItem toCartItemEntity(CartItemDTO cartItemDTO){
        CartItem cartItem = new CartItem();
        cartItem.setCartItemId(cartItemDTO.getCartItemId());
        cartItem.setCart(Cart.toCartEntity(cartItemDTO.getCartDTO()));
        cartItem.setProduct(cartItem.getProduct());
        cartItem.setCount(cartItem.getCount());

        return cartItem;
    }
}