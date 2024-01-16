package idusw.leafton.controller;

import ch.qos.logback.core.model.Model;
import idusw.leafton.model.DTO.*;
import idusw.leafton.model.entity.MainCategory;
import idusw.leafton.model.entity.Product;
import idusw.leafton.model.entity.SubCategory;
import idusw.leafton.model.service.*;
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
import java.util.ArrayList;
import java.util.List;

@RequestMapping(value = "/product")
@Controller
@RequiredArgsConstructor // 이 어노테이션은 final이나 @NonNull이 붙은 필드에 대한 생성자를 자동으로 생성해주는 Lombok의 어노테이션
public class ProductController {
    //product page mapping
    private final ProductService productService; //priavte final을 작성해줘야 @RequiredArgsConstructor 어노테이션 의존성 주입이됨
    private final ReviewService reviewService;
    private final MemberService memberService;
    private final MainCategoryService mainCategoryService;
    private final SubCategoryService subCategoryService;

    @GetMapping (value="/product/{productId}")
    public String goProduct(@PathVariable Long productId, HttpServletRequest request) {
        ProductDTO productid = productService.getProductDTOId(productId);
        ProductDTO productDTO = productService.viewDetailProduct(productId); //웹에 전달하기위해 객체생성 serviceimpl은 정보를 변한하기위해 사용됨
        List<ProductDTO> products = productService.viewAllProducts(); //추천상품
        List<ReviewDTO> review = reviewService.viewAllReviews(productid); //상품 리뷰 조회
        double ratingAvg = reviewService.getAvgRating(productId); //별점 평균 조회
        request.setAttribute("products", products);
        request.setAttribute("reviews", review);
        request.setAttribute("productDetail", productDTO);
        request.setAttribute("ratingAvg", ratingAvg);
        return "product/product";
    }

    //shop page mapping
    @GetMapping(value="/shop")
    public String goShop(HttpServletRequest request){
        List<ProductDTO> products = productService.viewAllProducts();
        List<MainCategoryDTO> mainCategorys = mainCategoryService.viewAllMainCategory(); //메인카테고리 조회
        request.setAttribute("products", products);
        request.setAttribute("mainCategorys", mainCategorys);
        return "product/shop";
    }

    @GetMapping(value="/shop/{mainCategoryId}")
    public String goSubCategory(@PathVariable Long mainCategoryId, HttpServletRequest request){
        MainCategoryDTO mainCategoryid = mainCategoryService.getMainCategoryById(mainCategoryId); //Long -> MainCategoryDTO
        List<ProductDTO> products = productService.viewProductsByMainCategory(mainCategoryid); //메인카테고리별 상품조회
        List<SubCategoryDTO> subCategories = subCategoryService.getSubCategoryByMainCategoryId(mainCategoryId); //서브카테고리 조회
        List<MainCategoryDTO> mainCategories = mainCategoryService.viewAllMainCategory(); //메인카테고리 선택후에도 기존에 메인카테고리 선택하기 위함
        MainCategoryDTO mainCategoryName = mainCategoryService.getMainCategoryName(mainCategoryId); //메인카테고리 선택시 유지이름또는 서브제목
        request.setAttribute("subCategories", subCategories);
        request.setAttribute("mainCategoryName", mainCategoryName);
        request.setAttribute("products", products);
        request.setAttribute("mainCategories", mainCategories);
        return "product/shop";
    }



    @GetMapping(value="/review/{memberId}/{productId}")
    public String goReview(@PathVariable("memberId") Long memberId ,@PathVariable("productId") Long productId,String reviewContent, Integer rating,HttpServletResponse httpServletResponse) throws IOException {
        MemberDTO memberid = memberService.getMemberById(memberId); // 패러미터로받은 Long형을 해당 객체에맞는 객체잠조로 변환 Long -> member
        ProductDTO productid = productService.getProductDTOId(productId);// 패러미터로받은 Long형을 해당 객체에맞는 객체잠조로 변환 Long -> product
        reviewService.insertReview(productid, memberid, reviewContent, rating);
        return "redirect:/product/product/" + productId;
    }
}
