package idusw.leafton.model.service;

import idusw.leafton.model.DTO.*;
import idusw.leafton.model.entity.Order;
import idusw.leafton.model.entity.OrderItem;

import java.util.List;

public interface OrderService {
    OrderDTO addOrder(OrderDTO orderDTO);

    OrderItemDTO addOrderItem(OrderDTO orderDTO, ProductDTO productDTO, int count);

    void decreaseStock(ProductDTO productDTO, int count);

    int calculateDeliveryFee(int totalPrice);

    List<OrderItemDTO> allUserOrderView(OrderDTO userOrders);

    List<OrderDTO> findMemberOrder(Long memberId);
}
