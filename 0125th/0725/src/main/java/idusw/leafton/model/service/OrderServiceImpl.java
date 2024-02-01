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
import org.springframework.stereotype.Service;


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

}
