package idusw.leafton.model.entity;

import idusw.leafton.model.DTO.ReviewDTO;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
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
    @JoinColumn(name = "memberId")
    private Member member;

    @Column
    private int rating;

    @Column
    private String content;

    @Column
    private LocalDate registDate;

    public static Review toReviewEntity(ReviewDTO reviewDTO){
        Review review = new Review();

        review.setReviewId(reviewDTO.getReviewId());
        review.setProduct(Product.toProductEntity(reviewDTO.getProductDTO())); //참조부분 직접 바로 메서드 사용하여 호출
        review.setMember(Member.toMemberEntity(reviewDTO.getMemberDTO()));
        review.setRating(reviewDTO.getRating());
        review.setContent(reviewDTO.getContent());
        review.setRegistDate(reviewDTO.getRegistDate());

        return review;
    }
}