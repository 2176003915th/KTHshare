package idusw.leafton.model.entity;

import idusw.leafton.model.DTO.CartDTO;
import idusw.leafton.model.DTO.MemberDTO;
import idusw.leafton.model.DTO.OrderDTO;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@Table(name = "order_table")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderId;

    @ManyToOne
    @JoinColumn(name = "memberId")
    private Member member;

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

    @Column String phone;
    public static Order toOrderEntity(OrderDTO orderDTO){
        Order order = new Order();
        order.setOrderId(orderDTO.getOrderId());
        order.setMember(Member.toMemberEntity(orderDTO.getMemberDTO()));
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

    public static Order createOrder(OrderDTO orderDTO){
        Order order = new Order();
        order.setMember(Member.toMemberEntity(orderDTO.getMemberDTO()));
        order.setAddress(orderDTO.getAddress());
        order.setZipcode(orderDTO.getZipcode());
        order.setOrderDate(LocalDate.now());
        order.setOrderPrice(orderDTO.getOrderPrice());
        order.setPaymentMethod(orderDTO.getPaymentMethod());
        order.setDeliveryFee(orderDTO.getDeliveryFee());
        order.setDeliveryPeriod(order.getOrderDate().plusDays(2)); //이게 동작할지 의문
        order.setDeliveryCompany(orderDTO.getDeliveryCompany()); //아직 테스트 값 지정
        order.setCustomerRequest(orderDTO.getCustomerRequest());
        order.setPhone(orderDTO.getPhone());

        return order;
    }
}