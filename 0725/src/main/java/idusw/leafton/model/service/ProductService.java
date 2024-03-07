package idusw.leafton.model.service;

import idusw.leafton.model.DTO.*;
import idusw.leafton.model.entity.Event;
import idusw.leafton.model.entity.MainCategory;
import idusw.leafton.model.entity.MainMaterial;
import idusw.leafton.model.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;


import java.util.List;

public interface ProductService { //ProductService 구현도
    List<ProductDTO> viewAllproduct();
    List<ProductDTO> viewProductsBySale(); //메인페이지에서 보여줄 할인 상품리스트
    List<ProductDTO> productDetailByMainCategory(Long mainCategoryId); //상품상세페이지 안에서 추천상품
    Page<ProductDTO> viewProducts(int pageNo, String arName); // 기본에서 정렬
    Page<ProductDTO> viewProductsByMainCategory(int pageNo, MainCategoryDTO mainCategoryDTO, String arName); // 메인카테고리 선택후에 정렬
    Page<ProductDTO> viewProductsBySubCategory(int pageNo, MainCategoryDTO mainCategoryDTO,SubCategoryDTO subCategoryDTO,String arName); //서브 카테고리 선택후에 정렬
    Page<ProductDTO> viewProductsByMainMaterial(int pageNo,MainMaterialDTO mainMaterialDTO, String arName); //매인재료만 선택후 정렬
    Page<ProductDTO> viewProductsByMcAndMm(int pageNo,MainCategoryDTO mainCategoryDTO, MainMaterialDTO mainMaterialDTO, String arName); //메인카테고리 - 메인재료 선택후 정렬
    Page<ProductDTO> viewProductsByMcAndScAndMm(int pageNo,MainCategoryDTO mainCategoryDTO, SubCategoryDTO subCategoryDTO, MainMaterialDTO mainMaterialDTO, String arName);
    Page<ProductDTO> viewProductsByEvent(int pageNo,EventDTO eventDTO, String arName);
    Page<ProductDTO> viewProductsByEventAndMainCategory(int pageNo,EventDTO eventDTO, MainCategoryDTO mainCategoryDTO, String arName);
    Page<ProductDTO> viewProductsByEventAndMcAndSc(int pageNo,EventDTO eventDTO, MainCategoryDTO mainCategoryDTO, SubCategoryDTO subCategoryDTO, String arName);
    Page<ProductDTO> viewProductsByEventAndMainMaterial(int pageNo,EventDTO eventDTO, MainMaterialDTO mainMaterialDTO, String arName);
    Page<ProductDTO> viewProductsByEventAndMcAndMm(int pageNo,EventDTO eventDTO, MainCategoryDTO mainCategoryDTO, MainMaterialDTO mainMaterialDTO, String arName);
    Page<ProductDTO> viewProductsByEventAndMcAndScAndMm(int pageNo,EventDTO eventDTO, MainCategoryDTO mainCategoryDTO, SubCategoryDTO subCategoryDTO, MainMaterialDTO mainMaterialDTO, String arName);
    ProductDTO viewDetailProduct(Long productId); //상품 상세 페이지 조회
    ProductDTO getProductById(Long productId);

    Page<ProductDTO> searchByProductName(int pageNo,String name, String arName);
    Page<ProductDTO> searchByMainCategoryName(int pageNo,String name,String arName);
    Page<ProductDTO> searchBySubCategoryName(int pageNo,String name,String arName);
    Page<ProductDTO> searchByMainMaterialName(int pageNo,String name,String arName);

}