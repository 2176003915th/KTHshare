package idusw.leafton.model.entity;

import idusw.leafton.model.DTO.CartDTO;
import idusw.leafton.model.DTO.MemberDTO;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name="cart")
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cartId")
    private Long cartId;

    @OneToOne
    @JoinColumn (name = "memberId") //fk 지정
    private Member member;

    public static Cart toCartEntity(CartDTO cartDTO){
        Cart cart = new Cart();
        cart.setCartId(cartDTO.getCartId());
        cart.setMember(Member.toMemberEntity(cartDTO.getMemberDTO()));

        return cart;
    }

    public static Cart createCart(MemberDTO member) {
        Cart cart = new Cart();
        cart.setMember(Member.toMemberEntity(member));
        return cart;
    }
}