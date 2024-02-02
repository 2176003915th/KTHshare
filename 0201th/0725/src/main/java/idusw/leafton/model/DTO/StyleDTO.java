package idusw.leafton.model.DTO;

import idusw.leafton.model.entity.Style;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class StyleDTO {
    private Long styleId;

    private String name;

    public static StyleDTO toStyleDTO(Style style) {
        StyleDTO styleDTO = new StyleDTO();

        styleDTO.setStyleId(style.getStyleId());
        styleDTO.setName(style.getName());

        return styleDTO;
    }
}
