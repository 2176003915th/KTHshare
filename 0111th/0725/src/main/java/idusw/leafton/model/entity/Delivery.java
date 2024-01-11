package idusw.leafton.model.entity;

import idusw.leafton.model.DTO.DeliveryDTO;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "delivery")
public class Delivery {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "deliveryId")
    private int deliveryId;

    @OneToOne
    @JoinColumn(name = "companyId")
    private Company company;

    @Column
    private LocalDateTime periodDate;

    @Column
    private String customerRequest;

    public static Delivery toDeliveryEntity(DeliveryDTO deliveryDTO){
        Delivery delivery = new Delivery();

        delivery.setDeliveryId(delivery.getDeliveryId());
        delivery.setCompany(delivery.getCompany());
        delivery.setPeriodDate(delivery.getPeriodDate());
        delivery.setCustomerRequest(delivery.getCustomerRequest());

        return delivery;
    }
}
