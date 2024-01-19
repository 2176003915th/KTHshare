package idusw.leafton.model.service;

import idusw.leafton.model.DTO.MainCategoryDTO;
import idusw.leafton.model.DTO.MainMaterialDTO;
import idusw.leafton.model.DTO.ProductDTO;
import idusw.leafton.model.DTO.SubCategoryDTO;
import idusw.leafton.model.entity.MainMaterial;
import idusw.leafton.model.entity.Product;


import java.util.List;

public interface ProductService { //ProductService 구현도
    public List<ProductDTO> viewAllProducts(); //List
    List<ProductDTO> viewProductsByMainCategory(MainCategoryDTO mainCategoryDTO); //메인카테고리별 상품 조회
    List<ProductDTO> viewProductsBySubCategory(MainCategoryDTO mainCategoryDTO,SubCategoryDTO subCategoryDTO); //서브카테고리별 상품조회
    List<ProductDTO> viewProductsByMainMaterial(MainMaterialDTO mainMaterialDTO); //메인재료별 상품 조회
    List<ProductDTO> viewProductsByMcAndMm(MainCategoryDTO mainCategoryDTO, MainMaterialDTO mainMaterialDTO);
    List<ProductDTO> viewProductsByMcAndScAndMm(MainCategoryDTO mainCategoryDTO, SubCategoryDTO subCategoryDTO, MainMaterialDTO mainMaterialDTO);
    ProductDTO viewDetailProduct(Long productId); //상품 상세 페이지 조회
    ProductDTO getProductById(Long productId);

}