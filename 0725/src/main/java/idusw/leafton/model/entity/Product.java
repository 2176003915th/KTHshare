package idusw.leafton.model.entity;

import idusw.leafton.model.DTO.ProductDTO;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Formula;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "Product")
public class Product {
    @Id // pk를 지정
    @GeneratedValue(strategy = GenerationType.IDENTITY) // auto_increment
    private Long productId;
    @ManyToOne
    @JoinColumn (name = "mainCategoryId") //fk지정
    private MainCategory mainCategory;
    @ManyToOne
    @JoinColumn (name = "subCategoryId") //fk지정
    private SubCategory subCategory;
    @ManyToOne
    @JoinColumn (name = "styleId") //fk 지정
    private Style style;
    @ManyToOne
    @JoinColumn (name = "eventId") //fk 지정
    private Event event;
    @ManyToOne
    @JoinColumn (name = "mainMaterialId")//fk 지정
    private MainMaterial mainMaterial;
    @Column
    private String content;
    @Column
    private Double rating;
    @Column
    private String maker;
    @Column
    private String name;
    @Column
    private String color;
    @Column
    private String size;
    @Column
    private Integer price;
    @Column
    private Integer salePercentage;
    @Formula("price - (price * sale_percentage / 100)")
    private BigDecimal calculatedPrice;
    @Column
    private Integer weight;
    @Column
    private Integer asPeriod;
    @Column
    private LocalDateTime registDate;
    @Column
    private Integer amount;
    @Column
    private Integer IsAssemble;
    @Column
    private String mainImage;
    @Column
    private String subImage;
    @Column
    private String thumbImage;


    public static Product toProductEntity(ProductDTO productDTO){

        Product product = new Product();
        product.setProductId(productDTO.getProductId());
        product.setMainCategory(MainCategory.toMainCategoryEntity(productDTO.getMainCategoryDTO()));
        product.setSubCategory(SubCategory.toSubCategoryEntity(productDTO.getSubCategoryDTO()));
        product.setMainMaterial(MainMaterial.toMainMaterialEntity(productDTO.getMainMaterialDTO()));
        product.setEvent(Event.toEventEntity(productDTO.getEventDTO()));
        product.setStyle(Style.toStyleEntity(productDTO.getStyleDTO()));
        product.setName(productDTO.getName());
        product.setContent(product.getContent());
        product.setMaker(product.getMaker());
        product.setRating(productDTO.getRating());
        product.setColor(productDTO.getColor());
        product.setSize(productDTO.getSize());
        product.setPrice(productDTO.getPrice());
        product.setSalePercentage(productDTO.getSalePercentage());
        product.setWeight(productDTO.getWeight());
        product.setAsPeriod(productDTO.getAsPeriod());
        product.setRegistDate(productDTO.getRegistDate());
        product.setAmount(productDTO.getAmount());
        product.setIsAssemble(productDTO.getIsAssemble());
        product.setMainImage(productDTO.getMainImage());
        product.setSubImage(productDTO.getSubImage());
        product.setThumbImage(productDTO.getThumbImage());

        return product;
    }
}
