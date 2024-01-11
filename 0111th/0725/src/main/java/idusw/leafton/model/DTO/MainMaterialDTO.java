package idusw.leafton.model.DTO;

import idusw.leafton.model.entity.MainMaterial;
import idusw.leafton.model.entity.SubMaterial;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class MainMaterialDTO {
    private Long mainMaterialId;
    private String name;

    public static MainMaterialDTO toMainMaterialDTO(MainMaterial mainMaterial) {
        MainMaterialDTO mainMaterialDTO = new MainMaterialDTO();

        mainMaterialDTO.setMainMaterialId(mainMaterial.getMainMaterialId());
        mainMaterialDTO.setName(mainMaterial.getName());

        return mainMaterialDTO;
    }
}
