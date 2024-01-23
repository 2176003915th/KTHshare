package idusw.leafton.model.service;

import idusw.leafton.model.DTO.MainCategoryDTO;
import idusw.leafton.model.DTO.MainMaterialDTO;
import idusw.leafton.model.DTO.ProductDTO;
import idusw.leafton.model.DTO.SubCategoryDTO;
import idusw.leafton.model.entity.MainCategory;
import idusw.leafton.model.entity.MainMaterial;
import idusw.leafton.model.entity.Product;


import java.util.List;

public interface ProductService { //ProductService 구현도
    List<ProductDTO> viewAllProducts(); //List
    List<ProductDTO> viewProductsByMainCategory(MainCategoryDTO mainCategoryDTO); //메인카테고리별 상품 조회
    List<ProductDTO> viewProductsBySubCategory(MainCategoryDTO mainCategoryDTO,SubCategoryDTO subCategoryDTO); //서브카테고리별 상품조회
    List<ProductDTO> viewProductsByMainMaterial(MainMaterialDTO mainMaterialDTO); //메인재료별 상품 조회
    List<ProductDTO> viewProductsByMcAndMm(MainCategoryDTO mainCategoryDTO, MainMaterialDTO mainMaterialDTO); //메인카테리 선택후에 재료 조회
    List<ProductDTO> viewProductsByMcAndScAndMm(MainCategoryDTO mainCategoryDTO, SubCategoryDTO subCategoryDTO, MainMaterialDTO mainMaterialDTO); //서브 카테고리 선택후에 재료조회
    List<ProductDTO> viewProductsOrderBy(String arName); // 기본에서 정렬
    List<ProductDTO> viewProductsByMainCategoryByOrderBy(MainCategoryDTO mainCategoryDTO,String arName); // 메인카테고리 선택후에 정렬
    List<ProductDTO> viewProductsBySubCategoryOrderBy(MainCategoryDTO mainCategoryDTO,SubCategoryDTO subCategoryDTO,String arName); //서브 카테고리 선택후에 정렬
    List<ProductDTO> viewProductsByMainMaterialOrderBy(MainMaterialDTO mainMaterialDTO, String arName); //매인재료만 선택후 정렬
    List<ProductDTO> viewProductsByMcAndMmOrderBy(MainCategoryDTO mainCategoryDTO, MainMaterialDTO mainMaterialDTO, String arName); //메인카테고리 - 메인재료 선택후 정렬
    List<ProductDTO> viewProductsByMcAndScAndMmOrderBy(MainCategoryDTO mainCategoryDTO, SubCategoryDTO subCategoryDTO, MainMaterialDTO mainMaterialDTO, String arName);
    ProductDTO viewDetailProduct(Long productId); //상품 상세 페이지 조회
    ProductDTO getProductById(Long productId);

}