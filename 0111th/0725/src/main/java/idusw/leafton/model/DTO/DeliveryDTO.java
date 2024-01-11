package idusw.leafton.model.DTO;

import idusw.leafton.model.entity.Company;
import idusw.leafton.model.entity.Delivery;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class DeliveryDTO {
    private int deliveryId;

    private Company company;

    private LocalDateTime periodDate;

    private String customerRequest;

    public static DeliveryDTO toDeliveryDTO(Delivery delivery){
        DeliveryDTO deliveryDTO = new DeliveryDTO();

        deliveryDTO.setDeliveryId(delivery.getDeliveryId());
        deliveryDTO.setCompany(delivery.getCompany());
        deliveryDTO.setPeriodDate(delivery.getPeriodDate());
        deliveryDTO.setCustomerRequest(delivery.getCustomerRequest());

        return deliveryDTO;
    }
}
