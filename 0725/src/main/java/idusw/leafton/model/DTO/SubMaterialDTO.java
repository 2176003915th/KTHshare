package idusw.leafton.model.DTO;

import idusw.leafton.model.entity.SubMaterial;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class SubMaterialDTO {
    private Long subMaterialId;
    private String name;

    public static SubMaterialDTO toSubMaterialDTO(SubMaterial subMaterial){
        SubMaterialDTO subMaterialDTO = new SubMaterialDTO();

        subMaterialDTO.setSubMaterialId(subMaterial.getSubMaterialId());
        subMaterialDTO.setName(subMaterial.getName());

        return subMaterialDTO;
    }
}