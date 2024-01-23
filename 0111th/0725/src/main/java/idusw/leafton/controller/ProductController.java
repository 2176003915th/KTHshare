package idusw.leafton.controller;

import ch.qos.logback.core.model.Model;
import idusw.leafton.model.DTO.*;
import idusw.leafton.model.entity.MainCategory;
import idusw.leafton.model.entity.MainMaterial;
import idusw.leafton.model.entity.Product;
import idusw.leafton.model.entity.SubCategory;
import idusw.leafton.model.service.*;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpServletRequest;


import java.io.IOException;
import java.time.LocalDate;
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
    private final MainMaterialService mainMaterialService;

    String viewName = null;
    ProductDTO productDTO = null;
    MainCategoryDTO mainCategoryDTO = null;
    SubCategoryDTO subCategoryDTO = null;
    MainMaterialDTO mainMaterialDTO = null;
    @GetMapping (value="/product/{productId}") //상품 상세 페이지
    public String goProduct(@PathVariable Long productId, HttpServletRequest request) {
        productDTO = productService.getProductById(productId);
        mainCategoryDTO = productDTO.getMainCategoryDTO(); //추천상품 위해 메인카테고리
        ProductDTO productDetail = productService.viewDetailProduct(productId); //웹에 전달하기위해 객체생성 serviceimpl은 정보를 변한하기위해 사용됨
        List<ProductDTO> products = productService.viewProductsByMainCategory(mainCategoryDTO); //추천상품
        List<ReviewDTO> review = reviewService.viewAllReviews(productDTO); //상품 리뷰 조회
        Double ratingAvg = reviewService.getAvgRating(productId); //별점 평균 조회
        request.setAttribute("products", products);
        request.setAttribute("reviews", review);
        request.setAttribute("productDetail", productDetail);
        request.setAttribute("ratingAvg", ratingAvg);
        return "product/product";
    }

    //shop page mapping
    @GetMapping(value="/shop") // 상품 리스트 처음 시작
    public String goShop(@RequestParam(value = "arName", required = false) String arName, HttpServletRequest request){
            List<MainCategoryDTO> mainCategories = mainCategoryService.viewAllMainCategory(); //메인카테고리 조회
            List<MainMaterialDTO> mainMaterials = mainMaterialService.viewAllMainMaterial(); //메인 재료 조회
           if(arName == null){
                List<ProductDTO> products = productService.viewAllProducts();
                request.setAttribute("products", products);
            } else if(arName != null) {
                List<ProductDTO> products = productService.viewProductsOrderBy(arName);
                request.setAttribute("products", products);
                   switch (arName){
                       case "name": viewName = "이름 순";
                           break;
                       case "new": viewName = "출시 순";
                           break;
                       case "rating": viewName = "평점 순";
                           break;
                       case "aprice": viewName = "가격: 가격 낮은 순";
                           break;
                       case "dprice": viewName = "가격: 가격 높은 순";
                   };
                   request.setAttribute("viewName", viewName);
            }

            request.setAttribute("mainCategories", mainCategories);
            request.setAttribute("mainMaterials", mainMaterials);


        return "product/shop";
    }

    @GetMapping(value="/shop/mainCategory") //메인카테고리 조회
    public String goMainCategory(@RequestParam("mainCategoryId") Long mainCategoryId,
                                 @RequestParam(value = "arName", required = false) String arName,HttpServletRequest request) {
        mainCategoryDTO = mainCategoryService.getMainCategoryById(mainCategoryId); //Long -> MainCategoryDTO
        List<SubCategoryDTO> subCategories = subCategoryService.getSubCategoryByMainCategoryId(mainCategoryDTO); //서브카테고리 조회
        List<MainMaterialDTO> mainMaterials = mainMaterialService.viewAllMainMaterial(); //메인 재료 조회
        List<MainCategoryDTO> mainCategories = mainCategoryService.viewAllMainCategory(); //메인카테고리 선택후에도 기존에 메인카테고리 선택하기 위함
        MainCategoryDTO mainCategoryDetail = mainCategoryService.getMainCategoryDetail(mainCategoryId); //메인카테고리 선택하면 메인카테고리메뉴에 해당이름 적용 또는 서브제목
        request.setAttribute("subCategories", subCategories);
        request.setAttribute("mainCategoryDetail", mainCategoryDetail);
        request.setAttribute("mainCategories", mainCategories);
        request.setAttribute("mainMaterials", mainMaterials);
        if (arName == null) {
            List<ProductDTO> products = productService.viewProductsByMainCategory(mainCategoryDTO); //메인카테고리별 상품조회
            request.setAttribute("products", products);
        } else if(arName !=null){
            List<ProductDTO> products = productService.viewProductsByMainCategoryByOrderBy(mainCategoryDTO, arName);
            request.setAttribute("products", products);
            switch (arName){
                case "name": viewName = "이름 순";
                    break;
                case "new": viewName = "출시 순";
                    break;
                case "rating": viewName = "평점 순";
                    break;
                case "aprice": viewName = "가격: 가격 낮은 순";
                    break;
                case "dprice": viewName = "가격: 가격 높은 순";
            };
            request.setAttribute("viewName", viewName);
        }
        return "product/shop";
    }
    @GetMapping(value="/shop/subCategory") //서브카테고리 조회
    public String goSubCategory(@RequestParam("mainCategoryId") Long mainCategoryId,
                                @RequestParam("subCategoryId") Long subCategoryId,
                                @RequestParam(value = "arName", required = false) String arName, HttpServletRequest request) {
        subCategoryDTO = subCategoryService.getSubCategoryById(subCategoryId); //Long -> SubCategoryDTO
        mainCategoryDTO = mainCategoryService.getMainCategoryById(mainCategoryId); //Long -> MainCategoryDTO
        List<SubCategoryDTO> subCategories = subCategoryService.getSubCategoryByMainCategoryId(mainCategoryDTO); //서브카테고리 조회
        List<MainCategoryDTO> mainCategories = mainCategoryService.viewAllMainCategory(); //서브카테고리 선택후에도 기존에 메인카테고리 선택하기 위함
        List<MainMaterialDTO> mainMaterials = mainMaterialService.viewAllMainMaterial(); //메인 재료 조회
        SubCategoryDTO subCategoryDetail = subCategoryService.getSubCategoryDetail(subCategoryId); //서브카테고리 선택하면 서브카테고리메뉴에 선택한 서브카테고리이름 적용
        MainCategoryDTO mainCategoryDetail = mainCategoryService.getMainCategoryDetail(mainCategoryId); //서브카테고리 선택후에도 기존에 선택한 메인카테고리 메뉴를 메인카테고리메뉴에 선택한해당메인카테고리이름 적
        request.setAttribute("subCategories", subCategories);
        request.setAttribute("mainCategoryDetail", mainCategoryDetail);
        request.setAttribute("subCategoryDetail",subCategoryDetail);
        request.setAttribute("mainCategories", mainCategories);
        request.setAttribute("mainMaterials", mainMaterials);
        if (arName == null) {
            List<ProductDTO> products = productService.viewProductsBySubCategory(mainCategoryDTO, subCategoryDTO); //서브카테고리별 상품조회
            request.setAttribute("products", products);
        } else if(arName !=null){
            List<ProductDTO> products = productService.viewProductsBySubCategoryOrderBy(mainCategoryDTO, subCategoryDTO, arName);
            request.setAttribute("products", products);
            switch (arName){
                case "name": viewName = "이름 순";
                    break;
                case "new": viewName = "출시 순";
                    break;
                case "rating": viewName = "평점 순";
                    break;
                case "aprice": viewName = "가격: 가격 낮은 순";
                    break;
                case "dprice": viewName = "가격: 가격 높은 순";
            };
            request.setAttribute("viewName", viewName);
        }
        return "product/shop";
    }

    @GetMapping(value="/shop/material") // 메인 재료만 조회
    public String goMainMaterial(@RequestParam("mainMaterialId") Long mainMaterialId,
                                 @RequestParam(value = "arName", required = false) String arName,HttpServletRequest request) {
        MainMaterialDTO mainMaterialDTO = mainMaterialService.getMainMaterialById(mainMaterialId); //Long -> MainMaterialDTO
        List<MainMaterialDTO> mainMaterials = mainMaterialService.viewAllMainMaterial(); //메인재료 선택후에도 메인 재료 선택하기 위함
        List<MainCategoryDTO> mainCategories = mainCategoryService.viewAllMainCategory(); //메인카테고리 선택후에도 메인 카테고리 선택하기 위함
        MainMaterialDTO mainMaterialDetail = mainMaterialService.getMainMaterialDetail(mainMaterialId); //메인재료 선택시 메인재료메뉴에 선택한 이름 유지
        request.setAttribute("mainMaterialDetail",mainMaterialDetail);
        request.setAttribute("mainMaterials", mainMaterials);
        request.setAttribute("mainCategories",mainCategories);
        if (arName == null) {
            List<ProductDTO> products = productService.viewProductsByMainMaterial(mainMaterialDTO); //메인재료 상품 검색
            request.setAttribute("products", products);
        } else if(arName !=null){
            List<ProductDTO> products = productService.viewProductsByMainMaterialOrderBy(mainMaterialDTO, arName);
            request.setAttribute("products", products);
            switch (arName){
                case "name": viewName = "이름 순";
                    break;
                case "new": viewName = "출시 순";
                    break;
                case "rating": viewName = "평점 순";
                    break;
                case "aprice": viewName = "가격: 가격 낮은 순";
                    break;
                case "dprice": viewName = "가격: 가격 높은 순";
            };
            request.setAttribute("viewName", viewName);
        }
        return "product/shop";
    }
    @GetMapping(value="/shop/mainCategory-material") // 메인카테고리선택된 상태에서 메인 재료 조회
    public String goMainCategoryAndMaterial(@RequestParam("mainCategoryId") Long mainCategoryId,
                                            @RequestParam("mainMaterialId") Long mainMaterialId,
                                            @RequestParam(value = "arName", required = false) String arName,HttpServletRequest request) {
        mainCategoryDTO = mainCategoryService.getMainCategoryById(mainCategoryId); //Long -> MainCategoryDTO
        mainMaterialDTO = mainMaterialService.getMainMaterialById(mainMaterialId); //Long -> MainMaterialDTO
        List<MainCategoryDTO> mainCategories = mainCategoryService.viewAllMainCategory(); //메인카테고리,메인재료 선택후에도 메인카테고리 선택하기 위함
        List<SubCategoryDTO> subCategories = subCategoryService.getSubCategoryByMainCategoryId(mainCategoryDTO); //서브카테고리 조회
        List<MainMaterialDTO> mainMaterials = mainMaterialService.viewAllMainMaterial(); //메인재료 선택후에도 메인 재료 조회하기 위함
        MainMaterialDTO mainMaterialDetail = mainMaterialService.getMainMaterialDetail(mainMaterialId); //메인재료,메인카테고리 선택시 메인재료메뉴에 선택한 이름 적용
        MainCategoryDTO mainCategoryDetail = mainCategoryService.getMainCategoryDetail(mainCategoryId); //메인재료,메인카테고리 선택시 메인카테고리메뉴에 해당이름 적용
        request.setAttribute("mainCategories", mainCategories);
        request.setAttribute("subCategories", subCategories);
        request.setAttribute("mainMaterials", mainMaterials);
        request.setAttribute("mainCategoryDetail", mainCategoryDetail);
        request.setAttribute("mainMaterialDetail", mainMaterialDetail);
        if (arName == null) {
            List<ProductDTO> products = productService.viewProductsByMcAndMm(mainCategoryDTO, mainMaterialDTO); //메인카테고리, 메인재료 상품조회
            request.setAttribute("products", products);
        } else if(arName !=null){
            List<ProductDTO> products = productService.viewProductsByMcAndMmOrderBy(mainCategoryDTO, mainMaterialDTO,arName);
            request.setAttribute("products", products);
            switch (arName){
                case "name": viewName = "이름 순";
                    break;
                case "new": viewName = "출시 순";
                    break;
                case "rating": viewName = "평점 순";
                    break;
                case "aprice": viewName = "가격: 가격 낮은 순";
                    break;
                case "dprice": viewName = "가격: 가격 높은 순";
            };
            request.setAttribute("viewName", viewName);
        }
        return "product/shop";
    }

    @GetMapping(value="/shop/mainCategory-subCategory-material") //서브카테고리 선택후에 메인 재료 선택 조회
    public String goMainCategoryAndSubCategoryAndMaterial(@RequestParam("mainCategoryId") Long mainCategoryId,
                                                          @RequestParam("subCategoryId") Long subCategoryId,
                                                          @RequestParam("mainMaterialId") Long mainMaterialId,
                                                          @RequestParam(value = "arName", required = false) String arName,HttpServletRequest request){
        mainCategoryDTO = mainCategoryService.getMainCategoryById(mainCategoryId); //Long -> MainCategoryDTO
        mainMaterialDTO = mainMaterialService.getMainMaterialById(mainMaterialId); //Long -> MainMaterialDTO
        subCategoryDTO = subCategoryService.getSubCategoryById(subCategoryId); //Long -> SubCategoryDTO
        List<MainCategoryDTO> mainCategories = mainCategoryService.viewAllMainCategory(); //서브카테고리-재료 선택후에도 기존에 메인카테고리 선택하기 위함
        List<SubCategoryDTO> subCategories = subCategoryService.getSubCategoryByMainCategoryId(mainCategoryDTO); //서브카테고리-재료 선택후에도 서브카테고리 선택하기위함
        List<MainMaterialDTO> mainMaterials = mainMaterialService.viewAllMainMaterial(); //서브카테고리-재료 선택후에도 메인 재료 선택하기위함
        SubCategoryDTO subCategoryDetail = subCategoryService.getSubCategoryDetail(subCategoryId); //서브카테고리 선택하면 서브카테고리메뉴에 선택한 서브카테고리이름 적용
        MainMaterialDTO mainMaterialDetail = mainMaterialService.getMainMaterialDetail(mainMaterialId); //메인재료,메인카테고리 선택시 메인재료메뉴에 선택한 이름 적용
        MainCategoryDTO mainCategoryDetail = mainCategoryService.getMainCategoryDetail(mainCategoryId); //메인재료,메인카테고리 선택시 메인카테고리메뉴에 해당이름 적용
        request.setAttribute("mainCategories", mainCategories);
        request.setAttribute("subCategories", subCategories);
        request.setAttribute("mainMaterials", mainMaterials);
        request.setAttribute("subCategoryDetail", subCategoryDetail);
        request.setAttribute("mainCategoryDetail", mainCategoryDetail);
        request.setAttribute("mainMaterialDetail", mainMaterialDetail);
        if (arName == null) {
            List<ProductDTO> products = productService.viewProductsByMcAndScAndMm(mainCategoryDTO,subCategoryDTO,mainMaterialDTO); //메인카테고리-서브카테고리,메인재료 상품조회
            request.setAttribute("products", products);
        } else if(arName !=null){
            List<ProductDTO> products = productService.viewProductsByMcAndScAndMmOrderBy(mainCategoryDTO,subCategoryDTO,mainMaterialDTO,arName);
            request.setAttribute("products", products);
            switch (arName){
                case "name": viewName = "이름 순";
                    break;
                case "new": viewName = "출시 순";
                    break;
                case "rating": viewName = "평점 순";
                    break;
                case "aprice": viewName = "가격: 가격 낮은 순";
                    break;
                case "dprice": viewName = "가격: 가격 높은 순";
            };
            request.setAttribute("viewName", viewName);
        }
        return "product/shop";
    }

    @PostMapping(value="/review") //리뷰작성
    public String goReview(HttpServletRequest request){
        ReviewDTO reviewDTO = new ReviewDTO();
        MemberDTO memberDTO = memberService.getMemberById(Long.valueOf(request.getParameter("memberId")));
        ProductDTO productDTO = productService.getProductById(Long.valueOf(request.getParameter("productId")));

        reviewDTO.setMemberDTO(memberDTO);
        reviewDTO.setProductDTO(productDTO);
        reviewDTO.setRating(Integer.valueOf(request.getParameter("rating")));
        reviewDTO.setContent(request.getParameter("reviewContent"));
        reviewDTO.setRegistDate(LocalDate.now());

        reviewService.insertReview(reviewDTO);
        return "redirect:/product/product/" + Long.valueOf(request.getParameter("productId"));
    }
}
