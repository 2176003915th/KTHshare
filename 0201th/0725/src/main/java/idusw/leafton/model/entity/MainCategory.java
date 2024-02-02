package idusw.leafton.model.entity;

import idusw.leafton.model.DTO.MainCategoryDTO;
import idusw.leafton.model.DTO.SubCategoryDTO;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name="Main_category")
public class MainCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // auto_increment
    @Column(name = "mainCategoryId")
    private Long mainCategoryId;

    @Column
    private String name;

    public static MainCategory toMainCategoryEntity(MainCategoryDTO mainCategoryDTO){
        MainCategory mainCategory = new MainCategory();

        mainCategory.setMainCategoryId(mainCategoryDTO.getMainCategoryId());
        mainCategory.setName(mainCategoryDTO.getName());

        return mainCategory;
    }
}
