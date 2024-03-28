package idusw.leafton.model.service;

import idusw.leafton.controller.ChartController;
import idusw.leafton.model.DTO.*;
import idusw.leafton.model.entity.Order;
import idusw.leafton.model.entity.OrderItem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface OrderService {
    OrderDTO addOrder(OrderDTO orderDTO);

    OrderItemDTO addOrderItem(OrderDTO orderDTO, ProductDTO productDTO, int count);

    void decreaseStock(ProductDTO productDTO, int count);

    int calculateDeliveryFee(int totalPrice);

    List<OrderItemDTO> allUserOrderView(OrderDTO userOrders);

    Page<OrderDTO> findMemberOrder(Long memberId, Pageable pageable);

    List<OrderDTO> findAll();
    List<Integer> getMonthRevenue();
    List<Integer> getMainCategoryRevenue(List<MainCategoryDTO>  mainCategoryDTOList);
    List<Integer> getStyleRevenue(List<StyleDTO> styleDTOList);
}
