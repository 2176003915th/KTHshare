package idusw.leafton.model.entity;

import idusw.leafton.model.DTO.OrderDTO;
import idusw.leafton.model.DTO.OrderItemDTO;
import idusw.leafton.model.DTO.ProductDTO;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.aspectj.weaver.ast.Or;

@Entity
@Getter
@Setter
@Table(name = "orderItem")
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderItemId;

    @ManyToOne
    @JoinColumn(name = "productId")
    private Product product;

    @ManyToOne
    @JoinColumn(name = "orderId")
    private Order order;

    @Column
    private int count;

    public static OrderItem toOrderItemEntity(OrderItemDTO orderItemDTO){
        OrderItem orderItem = new OrderItem();
        orderItem.setOrderItemId(orderItemDTO.getOrderItemId());
        orderItem.setProduct(Product.toProductEntity(orderItemDTO.getProductDTO()));
        orderItem.setOrder(Order.toOrderEntity(orderItemDTO.getOrderDTO()));
        orderItem.setCount(orderItemDTO.getCount());
        return orderItem;
    }
    public static OrderItem createOrderItem(OrderDTO order, ProductDTO product, int count) {
        OrderItem orderItem = new OrderItem();
        orderItem.setProduct(Product.toProductEntity(product));
        orderItem.setOrder(Order.toOrderEntity(order));
        orderItem.setCount(count);
        return orderItem;
    }
}
