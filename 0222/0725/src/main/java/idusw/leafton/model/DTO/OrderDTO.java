package idusw.leafton.model.DTO;

import idusw.leafton.model.entity.Order;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;


@Getter
@Setter
@NoArgsConstructor
@ToString
public class OrderDTO {

    private Long orderId;

    private MemberDTO memberDTO;

    private String address;

    private int zipcode;

    private LocalDate orderDate;

    private int orderPrice;

    private String paymentMethod;

    private int deliveryFee;

    private LocalDate deliveryPeriod;

    private String deliveryCompany;

    private String customerRequest;

    private String phone;

    public static OrderDTO toOrderDTO(Order order){
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setOrderId(order.getOrderId());
        orderDTO.setMemberDTO(MemberDTO.toMemberDTO(order.getMember()));
        orderDTO.setAddress(order.getAddress());
        orderDTO.setZipcode(order.getZipcode());
        orderDTO.setOrderDate(order.getOrderDate());
        orderDTO.setOrderPrice(order.getOrderPrice());
        orderDTO.setPaymentMethod(order.getPaymentMethod());
        orderDTO.setDeliveryFee(order.getDeliveryFee());
        orderDTO.setDeliveryCompany(order.getDeliveryCompany());
        orderDTO.setCustomerRequest(order.getCustomerRequest());
        orderDTO.setDeliveryPeriod(order.getDeliveryPeriod());
        orderDTO.setPhone(order.getPhone());

        return orderDTO;
    }
}