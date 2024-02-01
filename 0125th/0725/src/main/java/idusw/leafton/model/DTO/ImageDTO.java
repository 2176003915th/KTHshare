package idusw.leafton.model.DTO;

import idusw.leafton.model.entity.Image;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class ImageDTO {
    private Long imageId;
    private String mainImage;
    private String subImage;
    private String thumbImage;
    public static ImageDTO toimageDTO(Image image) {
        ImageDTO imageDTO = new ImageDTO();

        imageDTO.setImageId(image.getImageId());
        imageDTO.setMainImage(image.getMainImage());
        imageDTO.setSubImage(image.getSubImage());
        imageDTO.setThumbImage(image.getThumbImage());

        return imageDTO;
    }
}