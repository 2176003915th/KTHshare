package idusw.leafton.controller;

import ch.qos.logback.core.model.Model;
import idusw.leafton.model.DTO.MemberDTO;
import idusw.leafton.model.DTO.ProductDTO;
import idusw.leafton.model.DTO.ReviewDTO;
import idusw.leafton.model.entity.Product;
import idusw.leafton.model.service.ProductService;
import idusw.leafton.model.service.ReviewService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import jakarta.servlet.http.HttpServletRequest;


import java.io.IOException;
import java.util.List;

@RequestMapping(value = "/product")
@Controller
@RequiredArgsConstructor // 이 어노테이션은 final이나 @NonNull이 붙은 필드에 대한 생성자를 자동으로 생성해주는 Lombok의 어노테이션
public class ProductController {
    //product page mapping
    private final ProductService productService; //priavte final을 작성해줘야 @RequiredArgsConstructor 어노테이션 의존성 주입이됨
    private final ReviewService reviewService;

    @GetMapping (value="/product/{productId}")
    public String goProduct(@PathVariable Long productId, HttpServletRequest request) {
        ProductDTO productDTO = productService.viewDetailProduct(productId); //웹에 전달하기위해 객체생성 serviceimpl은 정보를 변한하기위해 사용됨
        List<ProductDTO> products = productService.viewAllProducts(); //추천상품
        List<ReviewDTO> review = reviewService.viewAllReviews(productId); //상품 리뷰 조회
        request.setAttribute("products", products);
        request.setAttribute("reviews", review);
        request.setAttribute("productDetail", productDTO);
        return "product/product";
    }

    //shop page mapping
    @GetMapping(value="/shop")
    public String goShop(HttpServletRequest request){
        List<ProductDTO> products = productService.viewAllProducts();
        request.setAttribute("products", products);
        return "product/shop";
    }

    @GetMapping(value="/review/{productId}")
    public String goReview(@PathVariable("productId") Long productId, String reviewContent, HttpServletResponse httpServletResponse) throws IOException {
        reviewService.insertReview(productId, reviewContent);
        return "redirect:/product/product/" + productId;
    }
}
