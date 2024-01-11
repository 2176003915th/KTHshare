package idusw.leafton.model.entity;

import idusw.leafton.model.DTO.OrderDTO;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@Table(name = "orderTable")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderId;

    @OneToOne
    @JoinColumn(name = "memberId")
    private Member member;

    @OneToOne
    @JoinColumn(name = "cartId")
    private Cart cart;

    @Column
    private String address;

    @Column
    private int zipcode;

    @DateTimeFormat(pattern = "yyyy-mm-dd")
    private LocalDate orderDate;

    @Column
    private int orderPrice;

    @Column
    private String paymentMethod;

    @Column
    private int deliveryFee;

    @Column
    private LocalDate deliveryPeriod;

    @Column
    private String deliveryCompany;

    @Column
    private String customerRequest;
    public static Order toOrderEntity(OrderDTO orderDTO){
        Order order = new Order();
        order.setOrderId(orderDTO.getOrderId());
        order.setMember(Member.toMemberEntity(orderDTO.getMemberDTO()));
        order.setCart(Cart.toCartEntity(orderDTO.getCartDTO()));
        order.setAddress(orderDTO.getAddress());
        order.setZipcode(orderDTO.getZipcode());
        order.setOrderDate(orderDTO.getOrderDate());
        order.setOrderPrice(orderDTO.getOrderPrice());
        order.setPaymentMethod(orderDTO.getPaymentMethod());
        order.setDeliveryFee(orderDTO.getDeliveryFee());
        order.setDeliveryPeriod(orderDTO.getDeliveryPeriod());
        order.setDeliveryCompany(orderDTO.getDeliveryCompany());
        order.setCustomerRequest(orderDTO.getCustomerRequest());

        return order;
    }
}