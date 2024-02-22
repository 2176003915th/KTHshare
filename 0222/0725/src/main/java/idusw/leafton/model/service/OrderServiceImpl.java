package idusw.leafton.model.service;

import idusw.leafton.model.DTO.OrderDTO;
import idusw.leafton.model.DTO.OrderItemDTO;
import idusw.leafton.model.DTO.ProductDTO;
import idusw.leafton.model.entity.Order;
import idusw.leafton.model.entity.OrderItem;
import idusw.leafton.model.entity.Product;
import idusw.leafton.model.repository.OrderItemRepository;
import idusw.leafton.model.repository.OrderRepository;
import idusw.leafton.model.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final ProductRepository productRepository;

    // 주문 생성
    @Override
    public OrderDTO addOrder(OrderDTO orderDTO){

        Order order = Order.createOrder(orderDTO);
        orderRepository.save(order);

        return OrderDTO.toOrderDTO(order);
    }
    // OrderItem 생성 후 주문한 상품 정보 저장
    @Override
    public OrderItemDTO addOrderItem(OrderDTO orderDTO, ProductDTO productDTO, int count){

        OrderItem orderItem = OrderItem.createOrderItem(orderDTO, productDTO, count);

        orderItemRepository.save(orderItem);

        return OrderItemDTO.toOrderItemDTO(orderItem);
    }
    // 상품 재고 감소
    @Override
    public void decreaseStock(ProductDTO productDTO, int count){

        productDTO.setAmount(productDTO.getAmount() - count);

        productRepository.save(Product.toProductEntity(productDTO));
    }
    // 배송비 계산
    @Override
    public int calculateDeliveryFee(int totalPrice) {
        if(totalPrice >= 100000) {
            return 0;
        } else {
            return 3000;
        }
    }
    //사용자 주문 정보로 해당하는 주문한 상품 찾기
    @Override
    public List<OrderItemDTO> allUserOrderView(OrderDTO userOrders){
        // 사용자의 모든 주문 아이템을 담아둘 리스트 선언
        List<OrderItemDTO> UserOrderItems = new ArrayList<>();

        // 사용자의 주문id를 찾아냄
        Long userOrderId = userOrders.getOrderId();

        // 해당 주문id에 대한 주문 아이템들을 찾아서 리스트에 추가
        List<OrderItem> orderItems = orderItemRepository.findByOrder_OrderId(userOrderId);

        // OrderItem을 OrderItemDTO로 변환하여 리스트에 추가
        for(OrderItem orderItem : orderItems){
            UserOrderItems.add(OrderItemDTO.toOrderItemDTO(orderItem));
        }

        return UserOrderItems;
    }
    // 사용자의 주문 정보 찾기 + 페이징 처리
    @Override
    public Page<OrderDTO> findMemberOrder(Long memberId, Pageable pageable){
        Page<Order> orderPage = orderRepository.findAllByMember_MemberId(memberId, pageable);

        List<OrderDTO> userOrder = new ArrayList<>();

        for(Order order : orderPage.getContent()){
            if(order.getMember().getMemberId() == memberId) {
                userOrder.add(OrderDTO.toOrderDTO(order));
            }
        }

        return new PageImpl<>(userOrder, pageable, orderPage.getTotalElements());
    }

}
