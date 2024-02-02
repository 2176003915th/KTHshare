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
public class MainCategoryDTO {

    private Long mainCategoryId;

    private String name;

    public static MainCategoryDTO toMainCategoryDTO(MainCategory mainCategory) {
        MainCategoryDTO mainCategoryDTO = new MainCategoryDTO();

        mainCategoryDTO.setMainCategoryId(mainCategory.getMainCategoryId());
        mainCategoryDTO.setName(mainCategory.getName());

        return mainCategoryDTO;
    }

}