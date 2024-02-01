package idusw.leafton.model.entity;


import idusw.leafton.model.DTO.SubCategoryDTO;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name="Sub_category")
public class SubCategory {

    @Id // pk를 지정
    @GeneratedValue(strategy = GenerationType.IDENTITY) // auto_increment
    private Long subCategoryId;

    @ManyToOne
    @JoinColumn(name = "mainCategoryId")
    private MainCategory mainCategory;
    @Column
    private String name;

    public static SubCategory toSubCategoryEntity(SubCategoryDTO subCategoryDTO){

        SubCategory subCategory = new SubCategory();

        subCategory.setSubCategoryId(subCategoryDTO.getSubCategoryId());
        subCategory.setMainCategory(MainCategory.toMainCategoryEntity(subCategoryDTO.getMainCategoryDTO()));
        subCategory.setName(subCategoryDTO.getName());

        return subCategory;
    }
}
