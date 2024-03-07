package idusw.leafton.model.DTO;

import idusw.leafton.model.entity.Address;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

//address entity의 데이터를 저장하는 객체
@Getter
@Setter
@NoArgsConstructor
public class AddressDTO {
    private Long addressId;

    private int zipcode;

    private String address;

    //entity 내의 정보를 현재 객체에 저장하는 메서드
    public static AddressDTO toAddressDTO(Address address){
        AddressDTO addressDTO = new AddressDTO();

        addressDTO.setAddressId(address.getAddressId());
        addressDTO.setZipcode(address.getZipcode());
        addressDTO.setAddress(address.getAddress());

        return addressDTO;
    }
}
