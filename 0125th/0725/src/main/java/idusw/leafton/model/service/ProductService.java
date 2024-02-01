package idusw.leafton.model.service;

import idusw.leafton.model.DTO.*;
import idusw.leafton.model.entity.Event;
import idusw.leafton.model.entity.MainCategory;
import idusw.leafton.model.entity.MainMaterial;
import idusw.leafton.model.entity.Product;


import java.util.List;

public interface ProductService { //ProductService 구현도
    List<ProductDTO> viewProducts(String arName); // 기본에서 정렬
    List<ProductDTO> viewProductsByMainCategory(MainCategoryDTO mainCategoryDTO);
    List<ProductDTO> viewProductsByMainCategory(MainCategoryDTO mainCategoryDTO,String arName); // 메인카테고리 선택후에 정렬
    List<ProductDTO> viewProductsBySubCategory(MainCategoryDTO mainCategoryDTO,SubCategoryDTO subCategoryDTO,String arName); //서브 카테고리 선택후에 정렬
    List<ProductDTO> viewProductsByMainMaterial(MainMaterialDTO mainMaterialDTO, String arName); //매인재료만 선택후 정렬
    List<ProductDTO> viewProductsByMcAndMm(MainCategoryDTO mainCategoryDTO, MainMaterialDTO mainMaterialDTO, String arName); //메인카테고리 - 메인재료 선택후 정렬
    List<ProductDTO> viewProductsByMcAndScAndMm(MainCategoryDTO mainCategoryDTO, SubCategoryDTO subCategoryDTO, MainMaterialDTO mainMaterialDTO, String arName);
    List<ProductDTO> viewProductsByEvent(EventDTO eventDTO, String arName);
    List<ProductDTO> viewProductsByEventAndMainCategory(EventDTO eventDTO, MainCategoryDTO mainCategoryDTO, String arName);
    List<ProductDTO> viewProductsByEventAndMcAndSc(EventDTO eventDTO, MainCategoryDTO mainCategoryDTO, SubCategoryDTO subCategoryDTO, String arName);
    List<ProductDTO> viewProductsByEventAndMainMaterial(EventDTO eventDTO, MainMaterialDTO mainMaterialDTO, String arName);
    List<ProductDTO> viewProductsByEventAndMcAndMm(EventDTO eventDTO, MainCategoryDTO mainCategoryDTO, MainMaterialDTO mainMaterialDTO, String arName);
    List<ProductDTO> viewProductsByEventAndMcAndScAndMm(EventDTO eventDTO, MainCategoryDTO mainCategoryDTO, SubCategoryDTO subCategoryDTO, MainMaterialDTO mainMaterialDTO, String arName);
    ProductDTO viewDetailProduct(Long productId); //상품 상세 페이지 조회
    ProductDTO getProductById(Long productId);

}