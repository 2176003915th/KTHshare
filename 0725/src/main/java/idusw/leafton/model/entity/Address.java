package idusw.leafton.model.entity;

import idusw.leafton.model.DTO.AddressDTO;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name="address")
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "addressId")
    private Long addressId;

    @Column
    private int zipcode;

    @Column
    private String address;

    public static Address toAddressEntity(AddressDTO addressDTO) {
        Address address = new Address();

        address.setAddressId(addressDTO.getAddressId());
        address.setZipcode(addressDTO.getZipcode());
        address.setAddress(addressDTO.getAddress());

        return address;
    }
}
