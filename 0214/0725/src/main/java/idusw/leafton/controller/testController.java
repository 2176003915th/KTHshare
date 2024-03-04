package idusw.leafton.controller;

import idusw.leafton.model.DTO.*;
import idusw.leafton.model.entity.Event;
import idusw.leafton.model.entity.MainMaterial;
import idusw.leafton.model.service.*;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpServletRequest;


import java.time.LocalDate;
import java.util.List;
import java.util.Optional;


@Controller
@RequiredArgsConstructor // 이 어노테이션은 final이나 @NonNull이 붙은 필드에 대한 생성자를 자동으로 생성해주는 Lombok의 어노테이션
public class testController {
    //product page mapping
    private final ProductService productService; //priavte final을 작성해줘야 @RequiredArgsConstructor 어노테이션 의존성 주입이됨
    private final ReviewService reviewService;
    private final MemberService memberService;
    private final MainCategoryService mainCategoryService;
    private final SubCategoryService subCategoryService;
    private final MainMaterialService mainMaterialService;
    private final EventService eventService;
    private final PostService postService;

    String viewName = null;
    String arPage = null;
    ProductDTO productDTO = null;
    MainCategoryDTO mainCategoryDTO = null;
    SubCategoryDTO subCategoryDTO = null;
    MainMaterialDTO mainMaterialDTO = null;
    EventDTO eventDTO = null;

    @GetMapping (value="/product/{productId}") //상품 상세 페이지
    public String goProduct(@PathVariable Long productId,
                            @RequestParam(required = false, defaultValue = "1", value = "p") int pageNo,
                            @RequestParam(required = false, defaultValue = "registDate", value = "criteria") String criteria,
                            HttpServletRequest request, HttpSession session) {
        productDTO = productService.getProductById(productId);
        Long mainCategoryId = null;
        if (session != null && session.getAttribute("memberDTO") != null) {
            MemberDTO memberDTO = (MemberDTO) session.getAttribute("memberDTO");
            System.out.println(memberDTO.getMemberId());
            ReviewDTO reviewDetail = reviewService.viewDetailReview(memberDTO, productDTO); // 리뷰 중복 확인 위함
            request.setAttribute("reviewDetail", reviewDetail);
        }
        mainCategoryDTO = productDTO.getMainCategoryDTO(); //추천상품 위해 메인카테고리
        mainCategoryId = mainCategoryDTO.getMainCategoryId();
        ProductDTO productDetail = productService.viewDetailProduct(productId); //웹에 전달하기위해 객체생성 serviceimpl은 정보를 변한하기위해 사용됨
//        List<ProductDTO> products = productService.productDetailByMainCategory(mainCategoryId); //추천상품
        List<ProductDTO> products = productService.viewAllproduct();

        pageNo = (pageNo == 0) ? 0 : (pageNo - 1);
        Page<ReviewDTO> reviewPageList = postService.getReviewPageList(pageNo, criteria, productDTO);
        int currentPage = reviewPageList.getNumber() + 1; //현재페이지 페이지는 0으로시작하기때문에 현재페이지와 맞추기위해 + 1을 설정함
        // 아래 startPage에서 사용자에게 1로 시작하는것을 보여주기위해 +1을 해줫기때문에 그페이지와 현재페이지를 맞추기위해서 + 1을함
        int blockLimit = 3;
        int startPage = (((int) Math.ceil(((double) (reviewPageList.getNumber() + 1) / blockLimit))) - 1) * blockLimit + 1;
        int endPage = Math.min((startPage + blockLimit - 1), reviewPageList.getTotalPages());

        Double ratingAvg = reviewService.getAvgRating(productId); //별점 평균 조회
        request.setAttribute("products", products);
        request.setAttribute("reviewPageList", reviewPageList);
        request.setAttribute("productDetail", productDetail);
        request.setAttribute("ratingAvg", ratingAvg);
        request.setAttribute("currentPage",currentPage);
        request.setAttribute("startPage",startPage);
        request.setAttribute("endPage",endPage);
        return "product/product";
    }



    //shop page mapping
    @GetMapping(value="/product/productList")
    public String goShop(@RequestParam(required = false, defaultValue = "0", value = "p") int pageNo,HttpServletRequest request){
        pageNo = (pageNo == 0) ? 0 : (pageNo - 1);
        Page<ProductDTO> products = productService.viewAllProducts(pageNo);
        int currentPage = products.getNumber() + 1; //현재페이지 페이지는 0으로시작하기때문에 현재페이지와 맞추기위해 + 1을 설정함
        // 아래 startPage에서 사용자에게 1로 시작하는것을 보여주기위해 +1을 해줫기때문에 그페이지와 현재페이지를 맞추기위해서 + 1을함
        int blockLimit = 2; //페이지 생성되는 리스트 개수
        int startPage = (((int) Math.ceil(((double) (currentPage) / blockLimit))) - 1) * blockLimit + 1;
        int endPage = Math.min((startPage + blockLimit - 1), products.getTotalPages());
        System.out.println(products.getTotalPages());
        request.setAttribute("products",products);
        request.setAttribute("startPage",startPage);
        request.setAttribute("endPage",endPage);
        request.setAttribute("currentPage",currentPage);

        return "product/testproduct";
    }

    void loadProductPage(Page<ProductDTO> products, HttpServletRequest request){ //product 페이지한것과 시작 페이지와 끝페이지를 매핑시키기위함
        int currentPage = products.getNumber() + 1; //현재페이지 페이지는 0으로시작하기때문에 현재페이지와 맞추기위해 + 1을 설정함
        // 아래 startPage에서 사용자에게 1로 시작하는것을 보여주기위해 +1을 해줫기때문에 그페이지와 현재페이지를 맞추기위해서 + 1을함
        int blockLimit = 2;
        int startPage = (((int) Math.ceil(((double) (products.getNumber() + 1) / blockLimit))) - 1) * blockLimit + 1;
        int endPage = Math.min((startPage + blockLimit - 1), products.getTotalPages());
        request.setAttribute("products",products);
        request.setAttribute("startPage",startPage);
        request.setAttribute("endPage",endPage);
        request.setAttribute("currentPage",currentPage);
    }
    void arrangeC(String arName, HttpServletRequest request){ //정렬선택 후에 선택한 정렬이름 메뉴표시 와 페이지에서 넘길때 정렬이 선택되었을때 정렬값 넘김
        if (arName == null) {
            arPage = "name";
        }
        else{
            switch (arName) {
                case "name":
                    viewName = "이름 순";
                    arPage = "name";
                    break;
                case "new":
                    viewName = "출시 순";
                    arPage = "new";
                    break;
                case "rating":
                    viewName = "평점 순";
                    arPage = "rating";
                    break;
                case "aprice":
                    viewName = "가격: 가격 낮은 순";
                    arPage = "aprice";
                    break;
                case "dprice":
                    viewName = "가격: 가격 높은 순";
                    arPage = "dprice";
                    break;
                default:
                    arPage = "name";
            }
            request.setAttribute("viewName", viewName);
        }
        request.setAttribute("arPage", arPage);
    }
    void mainCategoryList(HttpServletRequest request){
        List<MainCategoryDTO> mainCategories = mainCategoryService.viewAllMainCategory();
        request.setAttribute("mainCategories",mainCategories);
    }
    void mainCategoryDetail(Long mainCategoryId, HttpServletRequest request){
        MainCategoryDTO mainCategoryDetail = mainCategoryService.getMainCategoryDetail(mainCategoryId);
        request.setAttribute("mainCategoryDetail",mainCategoryDetail);
    }
    void subCategoryList(MainCategoryDTO mainCategoryDTO, HttpServletRequest request){
        List<SubCategoryDTO> subCategories = subCategoryService.getSubCategoryByMainCategoryId(mainCategoryDTO);
        request.setAttribute("subCategories",subCategories);
    }
    void subCategoryDetail(Long subCategoryId, HttpServletRequest request){
        SubCategoryDTO subCategoryDetail = subCategoryService.getSubCategoryDetail(subCategoryId);
        request.setAttribute("subCategoryDetail",subCategoryDetail);
    }
    void materialList(HttpServletRequest request){
        List<MainMaterialDTO> mainMaterials = mainMaterialService.viewAllMainMaterial();
        request.setAttribute("mainMaterials",mainMaterials);
    }
    void materialDetail(Long mainMaterialId, HttpServletRequest request){
        MainMaterialDTO mainMaterialDetail = mainMaterialService.getMainMaterialDetail(mainMaterialId);
        request.setAttribute("mainMaterialDetail",mainMaterialDetail);
    }



    public void goDefault(int pageNo, Long mainCategoryId, Long subCategoryId, Long mainMaterialId, String arName, Pageable pageable, HttpServletRequest request) {
            Page<ProductDTO> products = null;
        if (mainCategoryId == null && subCategoryId == null && mainMaterialId == null){ // 아무것도 선택되지않음
            products = productService.viewProducts(pageNo, arName);
        }
        else if (mainCategoryId != null && subCategoryId == null && mainMaterialId == null){ // 메인카테고리만 조회
            mainCategoryDTO = mainCategoryService.getMainCategoryById(mainCategoryId); //Long -> MainCategoryDTO
            mainCategoryDetail(mainCategoryId, request);
            subCategoryList(mainCategoryDTO, request);
            products = productService.viewProductsByMainCategory(pageNo, mainCategoryDTO, arName);
        }
        else if (mainCategoryId != null && subCategoryId != null && mainMaterialId == null){ //메인 카테고리 - 서브 카테고리 조회
            mainCategoryDTO = mainCategoryService.getMainCategoryById(mainCategoryId); //Long -> MainCategoryDTO
            subCategoryDTO = subCategoryService.getSubCategoryById(subCategoryId); //Long -> SubCategoryDTO
            subCategoryList(mainCategoryDTO, request); //메인 카테고리 선택했으니 서브 카테고리도 보여줌
            mainCategoryDetail(mainCategoryId, request); //메인카테고리 선택하면 메인카테고리메뉴에 해당이름 적용 또는 서브제목
            subCategoryDetail(subCategoryId, request);
            products = productService.viewProductsBySubCategory(pageNo, mainCategoryDTO, subCategoryDTO, arName);
        }
        else if (mainCategoryId == null && subCategoryId == null && mainMaterialId != null){ //메인 재료만 조회
            mainMaterialDTO = mainMaterialService.getMainMaterialById(mainMaterialId); //Long -> MainMaterialDTO
            materialDetail(mainMaterialId, request);
            products = productService.viewProductsByMainMaterial(pageNo, mainMaterialDTO, arName);
        }
        else if (mainCategoryId != null && subCategoryId == null && mainMaterialId != null){ //메인재료 - 메인카테고리 조회
            mainMaterialDTO = mainMaterialService.getMainMaterialById(mainMaterialId); //Long -> MainMaterialDTO
            mainCategoryDTO = mainCategoryService.getMainCategoryById(mainCategoryId); //Long -> MainCategoryDTO
            subCategoryList(mainCategoryDTO, request); //서브카테고리 조회
            mainCategoryDetail(mainCategoryId, request); //메인재료,메인카테고리 선택시 메인카테고리메뉴에 해당이름 적용
            materialDetail(mainMaterialId, request);
            products = productService.viewProductsByMcAndMm(pageNo, mainCategoryDTO, mainMaterialDTO, arName);
        }
        else if (mainCategoryId != null && subCategoryId != null && mainMaterialId != null){ //메인재료 - 메인카 - 서브카 조회
            mainCategoryDTO = mainCategoryService.getMainCategoryById(mainCategoryId); //Long -> MainCategoryDTO
            mainMaterialDTO = mainMaterialService.getMainMaterialById(mainMaterialId); //Long -> MainMaterialDTO
            subCategoryDTO = subCategoryService.getSubCategoryById(subCategoryId);
            subCategoryList(mainCategoryDTO, request); //서브카테고리 조회
            mainCategoryDetail(mainCategoryId, request); //메인재료,메인카테고리 선택시 메인카테고리메뉴에 해당이름 적용
            subCategoryDetail(subCategoryId, request); //서브카테고리 선택하면 서브카테고리메뉴에 선택한 서브카테고리이름 적용
            materialDetail(mainMaterialId, request);
            products = productService.viewProductsByMcAndScAndMm(pageNo,mainCategoryDTO,subCategoryDTO,mainMaterialDTO,arName);
        }

        loadProductPage(products, request);
        arrangeC(arName, request);
    }

    public void goEvent(int pageNo, Long eventId, Long mainCategoryId, Long subCategoryId, Long mainMaterialId, String arName, HttpServletRequest request){
        eventDTO = eventService.getEventById(eventId);
        Page<ProductDTO> products = null;
        if(mainCategoryId == null  && subCategoryId == null && mainMaterialId == null){ //이벤트 상품 조회
            products = productService.viewProductsByEvent(pageNo,eventDTO, arName);
        }
        else if(mainCategoryId != null && subCategoryId == null && mainMaterialId == null){ //이벤트 - 메인카테고리 조회
            mainCategoryDTO = mainCategoryService.getMainCategoryById(mainCategoryId); //Long -> MainCategoryDTO
            subCategoryList(mainCategoryDTO, request); //메인 카테고리 선택했으니 서브 카테고리도 보여줌
            mainCategoryDetail(mainCategoryId, request); //메인카테고리 선택하면 메인카테고리메뉴에 해당이름 적용 또는 서브제목
            products = productService.viewProductsByEventAndMainCategory(pageNo,eventDTO, mainCategoryDTO ,arName);
        }
        else if(mainCategoryId != null && subCategoryId != null && mainMaterialId == null){ //이벤트 - 메인카 - 서브카
            mainCategoryDTO = mainCategoryService.getMainCategoryById(mainCategoryId); //Long -> MainCategoryDTO
            subCategoryDTO = subCategoryService.getSubCategoryById(subCategoryId); //Long -> SubCategoryDTO
            subCategoryList(mainCategoryDTO, request);
            subCategoryDetail(subCategoryId, request);
            mainCategoryDetail(mainCategoryId, request); //메인카테고리 선택하면 메인카테고리메뉴에 해당이름 적용 또는 서브제목
            products = productService.viewProductsByEventAndMcAndSc(pageNo,eventDTO, mainCategoryDTO , subCategoryDTO, arName);
        }
        else if(mainCategoryId == null && subCategoryId == null && mainMaterialId != null){ //이벤트 - 재료 조회
            mainMaterialDTO = mainMaterialService.getMainMaterialById(mainMaterialId); //Long -> MainMaterialDTO
            materialDetail(mainMaterialId, request);
            products = productService.viewProductsByEventAndMainMaterial(pageNo,eventDTO, mainMaterialDTO, arName); //메인재료 상품 검색

        }
        else if(mainCategoryId != null && subCategoryId == null && mainMaterialId != null){
            mainCategoryDTO = mainCategoryService.getMainCategoryById(mainCategoryId);
            mainMaterialDTO = mainMaterialService.getMainMaterialById(mainMaterialId); //Long -> MainMaterialDTO
            subCategoryList(mainCategoryDTO, request); //메인 카테고리 선택했으니 서브 카테고리도 보여줌
            mainCategoryDetail(mainCategoryId, request); //메인카테고리 선택하면 메인카테고리메뉴에 해당이름 적용 또는 서브제목
            materialDetail(mainMaterialId, request);
            products = productService.viewProductsByEventAndMcAndMm(pageNo,eventDTO, mainCategoryDTO, mainMaterialDTO, arName);
        }
        else if(mainCategoryId != null && subCategoryId != null && mainMaterialId != null){
            mainCategoryDTO = mainCategoryService.getMainCategoryById(mainCategoryId); //Long -> MainCategoryDTO
            subCategoryDTO = subCategoryService.getSubCategoryById(subCategoryId); //Long -> SubCategoryDTO
            mainMaterialDTO = mainMaterialService.getMainMaterialById(mainMaterialId); //Long -> MainMaterialDTO
            subCategoryList(mainCategoryDTO, request);
            subCategoryDetail(subCategoryId, request);
            mainCategoryDetail(mainCategoryId, request); //메인카테고리 선택하면 메인카테고리메뉴에 해당이름 적용 또는 서브제목
            materialDetail(mainMaterialId, request);
            products = productService.viewProductsByEventAndMcAndScAndMm(pageNo,eventDTO, mainCategoryDTO, subCategoryDTO, mainMaterialDTO, arName);
        }
        loadProductPage(products, request);
        arrangeC(arName, request);
        request.setAttribute("eventDTO", eventDTO); //이벤트페이지라는것을 확인하기위함
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
    @GetMapping(value="/review/delete") //리뷰작성
    public String deleteReview(@RequestParam("reviewId") Long reviewId ,@RequestParam("productId") Long productId ,HttpServletRequest request){
        reviewService.deleteReview(reviewId);
        return "redirect:/product/product/" + productId;
    }
}
