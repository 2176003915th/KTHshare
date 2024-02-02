package idusw.leafton.model.service;

import idusw.leafton.model.DTO.CartDTO;
import idusw.leafton.model.DTO.CartItemDTO;
import idusw.leafton.model.DTO.MemberDTO;
import idusw.leafton.model.DTO.ProductDTO;
import idusw.leafton.model.entity.Cart;
import idusw.leafton.model.entity.CartItem;
import idusw.leafton.model.entity.Member;
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
    private final MemberService memberService;

    @Override
    public void addCart(MemberDTO member, ProductDTO newProduct, int count){
        Cart findCart = cartRepository.findByMember_MemberId(member.getMemberId()).orElse(null);

        // 장바구니가 존재하지 않는다면 장바구니 생성
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
        else { // 상품이 장바구니에 이미 존재하는 경우 수량만 증가
            CartItem update = cartItem;
            update.setCart(cartItem.getCart());
            update.setProduct(cartItem.getProduct());
            update.addCount(count);
            update.setCount(update.getCount());
            cartItemRepository.save(update);
        }

    }
    // DTO 변환 처리?
    // User라고 되어있는 부분 Member로 변경 및 리펙토링
    @Override
    public List<CartItemDTO> allUserCartView(CartDTO userCart){
        // 사용자의 카트 id를 꺼냄
        Long userCartId = userCart.getCartId();

        // id에 해당하는 유저가 담은 상품을 담아둘 곳
        List<CartItemDTO> UserCartItems = new ArrayList<>();

        // 유저 상관 없이 일단 카트에 있는 상품 모두 불러오기
        List<CartItem> CartItems = cartItemRepository.findAll();

        for(CartItem cartItem : CartItems) {
            if(cartItem.getCart().getCartId() == userCartId) {
                UserCartItems.add(CartItemDTO.toCartItemDTO(cartItem));
            }
        }
        return UserCartItems;
    }

    // 장바구니에 존재하는 상품들의 정보를 DB에서 찾아오기
    @Override
    public CartItemDTO findCartItemById(Long cartItem){
        Optional<CartItem> cartItems = cartItemRepository.findById(cartItem);
        if(cartItems.isPresent()){
            return CartItemDTO.toCartItemDTO(cartItems.get());
        }else {
            return null;
        }
    }

    // 사용자의 장바구니 정보 찾기
    @Override
    public CartDTO findMemberCart(Long memberId){
        Optional<Cart> memberCart = cartRepository.findByMember_MemberId(memberId);
        if(memberCart.isPresent()){
            return CartDTO.toCartDTO(memberCart.get());
        }else{
            // 장바구니가 없으면 새로운 장바구니를 생성
            MemberDTO member = memberService.getMemberById(memberId);
            Cart newCart = Cart.createCart(member);
            cartRepository.save(newCart);
            return CartDTO.toCartDTO(newCart);
        }
    }

    // 삭제 기능 수행
    @Override
    public void cartItemDelete(Long cartItemId){

        cartItemRepository.deleteById(cartItemId);
    }

    //바로 구매하기
    @Override
    public Long addOneCart(MemberDTO member, ProductDTO newProduct, int count){
        Cart findCart = cartRepository.findByMember_MemberId(member.getMemberId()).orElse(null);

        // 장바구니가 존재하지 않는다면 장바구니 생성
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
        else { // 상품이 장바구니에 이미 존재하는 경우 수량만 증가
            CartItem update = cartItem;
            update.setCart(cartItem.getCart());
            update.setProduct(cartItem.getProduct());
            update.addCount(count);
            update.setCount(update.getCount());
            cartItemRepository.save(update);
        }

        return cartItem.getCartItemId();

    }
    //전체 삭제 기능
    @Override
    public void deleteCart(Long cartId) {
        cartItemRepository.deleteByCart_CartId(cartId);
        cartRepository.deleteById(cartId);
    }


    @Override
    public void createCart(MemberDTO result){
        Cart.createCart(result);
    }


}
