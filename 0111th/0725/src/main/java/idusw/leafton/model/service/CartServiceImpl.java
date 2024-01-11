package idusw.leafton.model.service;

import idusw.leafton.model.DTO.CartDTO;
import idusw.leafton.model.DTO.CartItemDTO;
import idusw.leafton.model.DTO.MemberDTO;
import idusw.leafton.model.DTO.ProductDTO;
import idusw.leafton.model.entity.Cart;
import idusw.leafton.model.entity.CartItem;
import idusw.leafton.model.entity.Product;
import idusw.leafton.model.repository.CartItemRepository;
import idusw.leafton.model.repository.CartRepository;
import idusw.leafton.model.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService{
    private final CartRepository cartRepository;
    private final ProductRepository productRepository;
    private final CartItemRepository cartItemRepository;
    @Override
    public void addCart(MemberDTO member, ProductDTO newProduct, int count){
        Cart findCart = cartRepository.findByMember_MemberId(member.getMemberId()).orElse(null);

        //장바구니가 존재하지 않는다면 장바구니 생성
        if(findCart == null){
            findCart = Cart.createCart(member);
            cartRepository.save(findCart);
        }
        Product product = productRepository.findById(newProduct.getProductId()).orElse(null); //카트에 추가할 상품
        CartItem cartItem = cartItemRepository.findByCart_CartIdAndProduct_ProductId(
                findCart.getCartId(), newProduct.getProductId()).orElse(null); //장바구니에 있는 상품 찾기

        if(cartItem == null) {
            cartItem = CartItem.createCartItem(findCart, product, count);
            cartItemRepository.save(cartItem);
        }
        else { //상품이 장바구니에 이미 존재하는 경우 수량만 증가
            CartItem update = cartItem;
            update.setCart(cartItem.getCart());
            update.setProduct(cartItem.getProduct());
            update.addCount(count);
            update.setCount(update.getCount());
            cartItemRepository.save(update);
        }

    }
    @Override
    public CartDTO getCart(Long memberId) {
        Optional<Cart> byMemberMemberId = cartRepository.findByMember_MemberId(memberId);
        if(byMemberMemberId.isPresent()){
            return CartDTO.toCartDTO(byMemberMemberId.get());
        }else{
            return null;
        }
    }

    @Override
    public List<CartItem> allUserCartView(CartDTO userCart){
        //사용자의 카트 id를 꺼냄
        Long userCartId = userCart.getCartId();

        // id에 해당하는 유저가 담은 상품을 담아둘 곳
        List<CartItem> UserCartItems = new ArrayList<>();

        //유저 상관 없이 일단 카트에 있는 상품 모두 불러오기
        List<CartItem> CartItems = cartItemRepository.findAll();

        for(CartItem cartItem : CartItems) {
            if(cartItem.getCart().getCartId() == userCartId) {
                UserCartItems.add(cartItem);
            }
        }
        return UserCartItems;
    }

    @Override
    public List<CartItem> findCartItemByItemId(CartItemDTO itemId){

        return null;
    }
}
