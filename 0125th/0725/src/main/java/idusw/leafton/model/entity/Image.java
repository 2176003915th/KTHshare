package idusw.leafton.model.entity;

import idusw.leafton.model.DTO.ImageDTO;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table (name="Image")
public class Image {
    @Id // pk를 지정
    @GeneratedValue(strategy = GenerationType.IDENTITY) // auto_increment
    @Column (name = "imageId")
    private Long imageId;

    @Column
    private String mainImage;

    @Column
    private String subImage;

    @Column
    private String thumbImage;

    public static Image toImageEntity(ImageDTO imageDTO){
        Image image = new Image();

        image.setImageId(imageDTO.getImageId());
        image.setMainImage(imageDTO.getMainImage());
        image.setSubImage(imageDTO.getSubImage());
        image.setThumbImage(imageDTO.getThumbImage());

        return image;
    }
}