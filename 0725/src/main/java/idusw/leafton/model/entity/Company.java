package idusw.leafton.model.entity;

import idusw.leafton.model.DTO.CompanyDTO;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name= "Company")
public class Company {
    @Id // pk를 지정
    @GeneratedValue(strategy = GenerationType.IDENTITY) // auto_increment
    @Column (name = "companyId")
    private Long companyId;

    @OneToOne
    @JoinColumn (name = "addressId") // fk지정
    private Address addressId;

    @Column
    private String name;

    @Column
    private String callNumber;

    @Column
    private String owner;

    @Column
    private String email;

    public static Company toCompanyEntity(CompanyDTO companyDTO){
        Company company = new Company();
        company.setCompanyId(companyDTO.getCompanyId());
        company.setAddressId(companyDTO.getAddressId());
        company.setName(companyDTO.getName());
        company.setCallNumber(companyDTO.getCallNumber());
        company.setOwner(companyDTO.getOwner());
        company.setEmail(companyDTO.getEmail());

        return company;
    }
}