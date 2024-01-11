package idusw.leafton.model.entity;

import idusw.leafton.model.DTO.ReviewDTO;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "review")
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reviewId")
    private Long reviewId;

    @ManyToOne
    @JoinColumn(name = "productId" , referencedColumnName = "productId")
    private Product product;

    @ManyToOne
    @JoinColumn(name = "memerId")
    private Member memberId;

    @Column
    private int rating;

    @Column
    private String content;

    @Column
    private LocalDateTime registDate;

    public static Review toReviewEntity(ReviewDTO reviewDTO){
        Review review = new Review();

        review.setReviewId(reviewDTO.getReviewId());
        review.setProduct(Product.toProductEntity(reviewDTO.getProductDTO())); //참조부분 직접 바로 메서드 사용하여 호출
        review.setRating(reviewDTO.getRating());
        review.setContent(reviewDTO.getContent());
        review.setRegistDate(reviewDTO.getRegistDate());

        return review;
    }
}