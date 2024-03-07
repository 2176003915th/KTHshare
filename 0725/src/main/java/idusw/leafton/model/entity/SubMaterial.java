package idusw.leafton.model.entity;

import idusw.leafton.model.DTO.MainMaterialDTO;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table (name= "Sub_material")
public class SubMaterial {

    @Id // pk를 지정
    @GeneratedValue(strategy = GenerationType.IDENTITY) // auto_increment
    @Column(name = "subMaterialId")
    private Long subMaterialId;

    @Column
    private String name;

    public static SubMaterial toSubMaterialEntity(MainMaterialDTO mainMaterialDTO) {
        SubMaterial subMaterial = new SubMaterial();

        subMaterial.setSubMaterialId(mainMaterialDTO.getMainMaterialId());
        subMaterial.setName(mainMaterialDTO.getName());

        return subMaterial;
    }
}