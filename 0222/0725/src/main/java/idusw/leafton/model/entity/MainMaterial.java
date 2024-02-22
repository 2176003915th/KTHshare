package idusw.leafton.model.entity;
import idusw.leafton.model.DTO.MainMaterialDTO;
import idusw.leafton.model.DTO.SubCategoryDTO;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name="MainMaterial")
public class MainMaterial {

    @Id // pk를 지정
    @GeneratedValue(strategy = GenerationType.IDENTITY) // auto_increment
    @Column(name = "mainMaterialId")
    private Long mainMaterialId;
    @Column
    private String name;

    public static MainMaterial toMainMaterialEntity(MainMaterialDTO mainMaterialDTO) {
        MainMaterial mainMaterial = new MainMaterial();

        mainMaterial.setMainMaterialId(mainMaterialDTO.getMainMaterialId());
        mainMaterial.setName(mainMaterialDTO.getName());

        return mainMaterial;
    }
}