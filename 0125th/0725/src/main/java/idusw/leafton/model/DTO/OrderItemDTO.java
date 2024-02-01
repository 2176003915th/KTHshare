package idusw.leafton.model.DTO;

import idusw.leafton.model.entity.OrderItem;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class OrderItemDTO {
    private Long orderItemId;

    private ProductDTO productDTO;

    private OrderDTO orderDTO;

    private int count;

    public static OrderItemDTO toOrderItemDTO(OrderItem orderItem){
        OrderItemDTO orderItemDTO = new OrderItemDTO();
        orderItemDTO.setOrderItemId(orderItem.getOrderItemId());
        orderItemDTO.setProductDTO(ProductDTO.toProductDTO(orderItem.getProduct()));
        orderItemDTO.setOrderDTO(OrderDTO.toOrderDTO(orderItem.getOrder()));
        orderItemDTO.setCount(orderItem.getCount());

        return orderItemDTO;
    }

}
