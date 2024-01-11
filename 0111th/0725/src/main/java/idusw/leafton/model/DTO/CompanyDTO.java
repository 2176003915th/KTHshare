package idusw.leafton.model.DTO;

import idusw.leafton.model.entity.Address;
import idusw.leafton.model.entity.Company;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class CompanyDTO {
    private Long companyId;
    private Address addressId;
    private String name;
    private String callNumber;
    private String owner;
    private String email;

    public static CompanyDTO tocompanyDTO(Company company) {
        CompanyDTO companyDTO = new CompanyDTO();

        companyDTO.setCompanyId(company.getCompanyId());
        companyDTO.setAddressId(company.getAddressId());
        companyDTO.setName(company.getName());
        companyDTO.setCallNumber(company.getCallNumber());
        companyDTO.setOwner(company.getOwner());
        companyDTO.setEmail(company.getEmail());

        return companyDTO;
    }
}