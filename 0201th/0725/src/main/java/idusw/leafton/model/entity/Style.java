package idusw.leafton.model.entity;

import idusw.leafton.model.DTO.StyleDTO;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "style")
@Getter
@Setter
public class Style {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "styleId")
    private Long styleId;

    @Column
    private String name;

    public static Style toStyleEntity(StyleDTO styleDTO) {
        Style style = new Style();

        style.setStyleId(styleDTO.getStyleId());
        style.setName(styleDTO.getName());

        return style;
    }
}
