package idusw.leafton.model.DTO;

import idusw.leafton.model.entity.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class SubCategoryDTO {

    private Long subCategoryId;

    private MainCategoryDTO mainCategoryDTO;

    private String name;

    public static SubCategoryDTO toSubCategoryDTO(SubCategory subCategory) {

        SubCategoryDTO subCategoryDTO = new SubCategoryDTO();

        subCategoryDTO.setSubCategoryId(subCategory.getSubCategoryId());
        subCategoryDTO.setMainCategoryDTO(MainCategoryDTO.toMainCategoryDTO(subCategory.getMainCategory()));
        subCategoryDTO.setName(subCategory.getName());

        return subCategoryDTO;
    }

}