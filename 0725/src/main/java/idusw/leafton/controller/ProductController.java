package idusw.leafton.controller;


import idusw.leafton.model.DTO.*;
import idusw.leafton.model.FileSave;
import idusw.leafton.model.service.*;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.multipart.MultipartFile;


import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

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
    private final StyleService styleService;
    private final EventService eventService;
    private final PostService postService;
    private final FileSave fileSave;

    String viewName = null;
    String arPage = null;
    ProductDTO productDTO = null;
    MainCategoryDTO mainCategoryDTO = null;
    SubCategoryDTO subCategoryDTO = null;
    MainMaterialDTO mainMaterialDTO = null;
    EventDTO eventDTO = null;

    @GetMapping (value="product/product") //상품 상세 페이지
    public String goProduct(@RequestParam(required = false, value = "productId") Long productId,
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
        List<ProductDTO> products = productService.productDetailByMainCategory(mainCategoryId); //추천상품
//        List<ProductDTO> products = productService.view8product();

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
    @GetMapping(value="product/shop") // 상품 리스트 처음 시작
    public String goShop(@RequestParam(value = "arName", required = false) String arName,
                         @RequestParam(value = "mainCategoryId", required = false) Long mainCategoryId,
                         @RequestParam(value = "subCategoryId", required = false) Long subCategoryId,
                         @RequestParam(value = "mainMaterialId", required = false) Long mainMaterialId,
                         @RequestParam(value = "eventId", required = false) Long eventId,
                         @RequestParam(value = "searchType", required = false) String searchType,
                         @RequestParam(value = "searchValue", required = false) String searchValue,
                         @RequestParam(required = false, defaultValue = "0", value = "p") int pageNo,
                         Pageable pageable,HttpServletRequest request){
        mainCategoryList(request); //메인카테고리 메뉴 조회
        materialList(request);//메인 재료 메뉴 조회
        pageNo = (pageNo == 0) ? 0 : (pageNo - 1);

        if(searchType != null) {
            Page<ProductDTO> products = null;
            //검색 분류에 따른 productList 가져오기
            switch (searchType) {
                case "category":
                    products = productService.searchBySubCategoryName(pageNo, searchValue ,arName);
                    if(products.isEmpty()) {
                        products = productService.searchByMainCategoryName(pageNo, searchValue, arName);
                    }
                    break;
                case "productName":
                    products = productService.searchByProductName(pageNo, searchValue, arName);
                    break;
                case "material":
                    products = productService.searchByMainMaterialName(pageNo, searchValue, arName);
                    break;
            }
            if(products == null || products.isEmpty()) {//검색결과가 없을 경우
                request.setAttribute("message", "검색 결과가 없습니다");
                request.setAttribute("mainCategoryList", mainCategoryService.viewAllMainCategory());
                request.setAttribute("eventList", eventService.getAll());
                return "/main/index";
            } else {
                loadProductPage(products, request);
                arrangeC(arName, request);
                request.setAttribute("searchType",searchType);
                request.setAttribute("searchValue",searchValue);
            }

        } else if(eventId != null){
            goEvent(pageNo, eventId, mainCategoryId, subCategoryId, mainMaterialId, arName, request);
        }
        else { //디폴트 기본값
            goDefault(pageNo, mainCategoryId, subCategoryId, mainMaterialId, arName, pageable, request);
        }

        return "product/shop";
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




    @PostMapping(value="product/review") //리뷰작성
    public String goReview(HttpServletRequest request){
        ReviewDTO reviewDTO = new ReviewDTO();
        MemberDTO memberDTO = memberService.getMemberById(Long.valueOf(request.getParameter("memberId")));
        productDTO = productService.getProductById(Long.valueOf(request.getParameter("productId")));

        reviewDTO.setMemberDTO(memberDTO);
        reviewDTO.setProductDTO(productDTO);
        reviewDTO.setRating(Integer.valueOf(request.getParameter("rating")));
        reviewDTO.setContent(request.getParameter("reviewContent"));
        reviewDTO.setRegistDate(LocalDate.now());

        reviewService.insertReview(reviewDTO);
        return "redirect:/product/product?productId=" + Long.valueOf(request.getParameter("productId"));
    }
    @PostMapping(value="product/review/delete") //리뷰삭제
    public String deleteReview(@RequestParam("reviewId") Long reviewId ,@RequestParam("productId") Long productId ,HttpServletRequest request){
        reviewService.deleteReview(reviewId);
        return "redirect:/product/product?productId=" + productId;
    }

    /*---admin start---*/

    @GetMapping(value = "admin/product/list")
    public String goAdminList(HttpServletRequest request) {
        request.setAttribute("products", productService.viewAllproduct());
        return "admin/product/list";
    }



    @GetMapping(value="admin/property/main-category/list")
    public String goMainCategoryTable(HttpServletRequest request){
        mainCategoryList(request);
        return "admin/property/main-category/list";
    }
    @GetMapping(value="admin/property/sub-category/list")
    public String goSubCategoryTable(HttpServletRequest request){
        List<SubCategoryDTO> subCategoryList = subCategoryService.viewAllSubCategory();
        request.setAttribute("subCategoryList", subCategoryList);
        return "admin/property/sub-category/list";
    }
    @GetMapping(value="admin/property/main-material/list")
    public String goMainMaterialTable(HttpServletRequest request){
        materialList(request);
        return "admin/property/main-material/list";
    }


    @GetMapping(value="admin/property/main-category/register")
    private String mainCategoryRegister(){
        return "admin/property/main-category/register";
    }
    @GetMapping(value="admin/property/sub-category/register")
    public String subCategoryRegister(HttpServletRequest request){
        mainCategoryList(request);
        return "admin/property/sub-category/register";
    }
    @GetMapping(value="admin/property/main-material/register")
    public String mainMaterialRegister(){
        return "admin/property/main-material/register";
    }


    @GetMapping(value="admin/property/main-category/edit")
    public String mainCategoryEdit(@RequestParam(value = "mainCategoryId", required = false) Long mainCategoryId, HttpServletRequest request){
        MainCategoryDTO mainCategoryDTO = mainCategoryService.getMainCategoryById(mainCategoryId);
        mainCategoryDetail(mainCategoryId, request);
        request.setAttribute("mainCategoryDTO",mainCategoryDTO);
        return "admin/property/main-category/edit";
    }

    @GetMapping(value="admin/property/sub-category/edit")
    public String subCategoryEdit(@RequestParam(value = "subCategoryId", required = false) Long subCategoryId,HttpServletRequest request){
        subCategoryDetail(subCategoryId, request);
        mainCategoryList(request);
        request.setAttribute("subCategoryDTO",subCategoryDTO);
        return "admin/property/sub-category/edit";
    }

    @GetMapping(value="admin/property/main-material/edit")
    public String mainMaterialEdit(@RequestParam(value = "mainMaterialId", required = false) Long mainMaterialId, HttpServletRequest request){
        materialDetail(mainMaterialId, request);
        return "admin/property/main-material/edit";
    }


    @PostMapping(value="admin/insert")
    public String insert(@RequestParam(value = "type", required = false) String type,
                         @RequestParam("mc-image") MultipartFile image, HttpServletRequest request) throws IOException {
        if("mainCategory".equals(type)){
            MainCategoryDTO mainCategoryDTO = new MainCategoryDTO();
            mainCategoryDTO.setName(request.getParameter("mc-name"));

            mainCategoryService.insertAndUpdateMainCategory(mainCategoryDTO, image);
            return "redirect:/admin/property/main-category/list";
        }
        else if("subCategory".equals(type)){
            SubCategoryDTO subCategoryDTO = new SubCategoryDTO();
            mainCategoryDTO = mainCategoryService.getMainCategoryById(Long.valueOf(request.getParameter("mainCategoryId")));
            subCategoryDTO.setMainCategoryDTO(mainCategoryDTO);
            subCategoryDTO.setName(request.getParameter("sc-name"));

            subCategoryService.insertAndUpdateSubCategory(subCategoryDTO);
            return "redirect:/admin/property/sub-category/list";
        }
        else {
            MainMaterialDTO mainMaterialDTO = new MainMaterialDTO();
            mainMaterialDTO.setName(request.getParameter("mm-name"));

            mainMaterialService.insertAndUpdateMainMaterial(mainMaterialDTO);
            return "redirect:/admin/property/main-material/list";
        }
    }
    @PostMapping(value="admin/update")
    private String update(@RequestParam(value = "type", required = false) String type ,
                          @RequestParam("mc-image") MultipartFile image, HttpServletRequest request ) throws IOException {
        if("mainCategory".equals(type)){
            Long id = Long.valueOf(request.getParameter("mc-id"));
            MainCategoryDTO mainCategoryDTO = mainCategoryService.getMainCategoryById(id);
            mainCategoryDTO.setName(request.getParameter("mc-name"));
            mainCategoryService.insertAndUpdateMainCategory(mainCategoryDTO, image);
            return "redirect:/admin/property/main-category/list";
        }
        else if("subCategory".equals(type)){
            Long id = Long.valueOf(request.getParameter("sc-id"));
            SubCategoryDTO subCategoryDTO = subCategoryService.getSubCategoryById(id);
            mainCategoryDTO = mainCategoryService.getMainCategoryById(Long.valueOf(request.getParameter("mainCategoryId")));
            subCategoryDTO.setMainCategoryDTO(mainCategoryDTO);
            subCategoryDTO.setName(request.getParameter("sc-name"));

            subCategoryService.insertAndUpdateSubCategory(subCategoryDTO);
            return "redirect:/admin/property/sub-category/list";
        }
        else {
            Long id = Long.valueOf(request.getParameter("mm-id"));
            MainMaterialDTO mainMaterialDTO = mainMaterialService.getMainMaterialById(id);
            mainMaterialDTO.setName(request.getParameter("mm-name"));

            mainMaterialService.insertAndUpdateMainMaterial(mainMaterialDTO);
            return "redirect:/admin/property/main-material/list";
        }
    }
    @GetMapping(value = "admin/product/register")
    public String goAdminProductRegister(@RequestParam(required = false) String mainCategoryId,
                                         @RequestParam(required = false) String subCategoryId,
                                         @RequestParam(required = false) String mainMaterialId,
                                         @RequestParam(required = false) String styleId,
                                         @RequestParam(required = false) String eventId,
                                         HttpServletRequest request) {
        request.setAttribute("mainCategories", mainCategoryService.viewAllMainCategory());
        request.setAttribute("subCategories", subCategoryService.getAll());
        request.setAttribute("mainCategoryNumber", "1");
        request.setAttribute("mainMaterials", mainMaterialService.viewAllMainMaterial());
        request.setAttribute("styles", styleService.getAll());
        request.setAttribute("events", eventService.getAll());

        if(mainCategoryId != null) {//메인 카테고리 selectBox 변경했을 경우 변경된 나머지 selectBox의 데이터를 다시 request에 저장
            request.setAttribute("mainCategoryNumber", mainCategoryId);
            if(subCategoryId != null) request.setAttribute("subCategoryNumber", subCategoryId);
            if(mainMaterialId != null) request.setAttribute("mainMaterialNumber", mainMaterialId);
            if(styleId != null) request.setAttribute("styleNumber", styleId);
            if(eventId != null) request.setAttribute("eventNumber", eventId);
        }

        return "admin/product/register";
    }

    @PostMapping(value = "admin/product/register")
    private String insertProduct(@ModelAttribute ProductDTO productDTO,
                                 HttpServletRequest request,
                                 @RequestParam("thumb") MultipartFile thumb,
                                 @RequestParam("main") MultipartFile main,
                                 @RequestParam("sub") MultipartFile sub) throws IOException {
        StyleDTO styleDTO = styleService.getById(Long.valueOf(request.getParameter("style")));
        SubCategoryDTO subCategoryDTO = subCategoryService.getSubCategoryDetail(Long.valueOf(request.getParameter("subCategory")));
        MainCategoryDTO mainCategoryDTO = mainCategoryService.getMainCategoryById(Long.valueOf(request.getParameter("mainCategory")));
        MainMaterialDTO mainMaterialDTO = mainMaterialService.getMainMaterialById(Long.valueOf(request.getParameter("mainMaterial")));
        EventDTO eventDTO = eventService.getEventById(Long.valueOf(request.getParameter("event")));
        String width = request.getParameter("width");
        String height = request.getParameter("height");
        String depth = request.getParameter("depth");
        String size = width + "fm" + depth + "lm" + height;

        productDTO.setStyleDTO(styleDTO);
        productDTO.setSubCategoryDTO(subCategoryDTO);
        productDTO.setMainCategoryDTO(mainCategoryDTO);
        productDTO.setMainMaterialDTO(mainMaterialDTO);
        productDTO.setEventDTO(eventDTO);
        productDTO.setSize(size);
        productDTO.setSalePercentage(0);
        productDTO.setRegistDate(LocalDateTime.now());

        productService.saveProduct(productDTO, main, thumb, sub);

        return "redirect:/admin/product/list";
    }

    @GetMapping(value = "admin/product/edit")
    public String goAdminProductEdit(@RequestParam(value ="mainCategoryId", required = false) String mainCategoryId,
                                     HttpServletRequest request) {
        request.setAttribute("mainCategories", mainCategoryService.viewAllMainCategory());
        request.setAttribute("subCategories", subCategoryService.getAll());
        request.setAttribute("styles", styleService.getAll());
        request.setAttribute("events", eventService.getAll());
        request.setAttribute("mainMaterials", mainMaterialService.viewAllMainMaterial());

        Long productId = Long.valueOf(request.getParameter("productId"));
        ProductDTO productDTO = productService.getProductById(productId);
        request.setAttribute("product", productDTO);

        //메인 카테고리에 맞는 서브 카테고리
        if(mainCategoryId != null) {
            request.setAttribute("mainCategoryNumber", mainCategoryId);
        }

        return "admin/product/edit";
    }

    @PostMapping(value = "admin/product/edit")
    private String updateProduct(@ModelAttribute ProductDTO productDTO,
                                 HttpServletRequest request,
                                 @RequestParam(value = "thumbFile", required = false) MultipartFile thumbFile,
                                 @RequestParam(value = "mainFile", required = false) MultipartFile mainFile,
                                 @RequestParam(value = "subFile", required = false) MultipartFile subFile) throws IOException {
        StyleDTO styleDTO = styleService.getById(Long.valueOf(request.getParameter("style")));
        SubCategoryDTO subCategoryDTO = subCategoryService.getSubCategoryDetail(Long.valueOf(request.getParameter("subCategory")));
        MainCategoryDTO mainCategoryDTO = mainCategoryService.getMainCategoryById(Long.valueOf(request.getParameter("mainCategory")));
        MainMaterialDTO mainMaterialDTO = mainMaterialService.getMainMaterialById(Long.valueOf(request.getParameter("mainMaterial")));
        EventDTO eventDTO = eventService.getEventById(Long.valueOf(request.getParameter("event")));
        String width = request.getParameter("width");
        String height = request.getParameter("height");
        String depth = request.getParameter("depth");
        String size = width + "fm" + depth + "lm" + height;

        System.out.println("상품 설명:"+productDTO.getContent());

        productDTO.setStyleDTO(styleDTO);
        productDTO.setSubCategoryDTO(subCategoryDTO);
        productDTO.setMainCategoryDTO(mainCategoryDTO);
        productDTO.setMainMaterialDTO(mainMaterialDTO);
        productDTO.setEventDTO(eventDTO);
        productDTO.setSize(size);
        productDTO.setSalePercentage(0);
        productDTO.setRegistDate(LocalDateTime.now());

        System.out.println("현재 이벤트: "+productDTO.getEventDTO().getTitle());

        productService.saveProduct(productDTO, mainFile, thumbFile, subFile);

        return "redirect:/admin/product/list";
    }




    /*---admin end---*/
}
