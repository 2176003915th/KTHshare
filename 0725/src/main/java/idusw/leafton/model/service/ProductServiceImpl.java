package idusw.leafton.model.service;

import idusw.leafton.model.DTO.*;
import idusw.leafton.model.entity.*;
import idusw.leafton.model.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import idusw.leafton.model.repository.ProductRepository;

import java.util.*;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService { //ProductService를 구현도를 받고 implements를 구현함

    private final ProductRepository productRepository; //객체의 상태를 저장하고, 해당 상태를 클래스 내의 여러 메서드에서 공유하거나 조작하기 위해 필드 선언
    private final ReviewRepository reviewRepository;

    public Pageable arrangeService(String arName , int pageNo) { //정렬
        Sort sort = null;
        if (arName == null) {
            sort = Sort.by(Sort.Direction.ASC, "name");
        } else {
            switch (arName) {
                case "name":
                    sort = Sort.by(Sort.Direction.ASC, "name");
                    break;
                case "new":
                    sort = Sort.by(Sort.Direction.ASC, "registDate");
                    break;
                case "rating":
                    sort = Sort.by(Sort.Direction.DESC, "rating");
                    break;
                case "aprice":
                    sort = Sort.by(Sort.Direction.ASC, "calculatedPrice");
                    break;
                case "dprice":
                    sort = Sort.by(Sort.Direction.DESC, "calculatedPrice");
            };
        }
        Pageable pageable = PageRequest.of(pageNo, 4, sort);
        return pageable;
    }
    void combineRating(Page<Product> productList){ // 평점 평균값을 product 테이블 필드 평점에 대입
        for(Product product: productList) {
            Optional<Double> opAvgRating = reviewRepository.getAverageRatingByProduct(product.getProductId()); //평균평점을 구함
            product.setRating(opAvgRating.orElse(0.0)); //평균평점을 구한것을 product테이블 rating 컬럼에 set함
        }
        productRepository.saveAll(productList); //다시 저장

    }

    @Override
    public List<ProductDTO> productDetailByMainCategory(Long mainCategoryId){
        List<Product> productList = productRepository.findRandomProductsByMainCategory(mainCategoryId);
        List<ProductDTO> productDTOList = new ArrayList<>();
        for(Product product: productList) {
            Optional<Double> opAvgRating = reviewRepository.getAverageRatingByProduct(product.getProductId()); //평균평점을 구함
            product.setRating(opAvgRating.orElse(0.0)); //평균평점을 구한것을 product테이블 rating 컬럼에 set함
        }
        productRepository.saveAll(productList); //다시 저장
        for(Product product: productList) {
            productDTOList.add(ProductDTO.toProductDTO(product)); //productDTO 객체안에 DTO 데이터 넣음
        }
        return productDTOList;
    }

    @Override
    public List<ProductDTO> viewAllproduct(){
        List<Product> productList = productRepository.findRandomProducts();
        List<ProductDTO> productDTOList = new ArrayList<>();
        for(Product product: productList) {
            Optional<Double> opAvgRating = reviewRepository.getAverageRatingByProduct(product.getProductId()); //평균평점을 구함
            product.setRating(opAvgRating.orElse(0.0)); //평균평점을 구한것을 product테이블 rating 컬럼에 set함
        }
        productRepository.saveAll(productList); //다시 저장
        for(Product product: productList) {
            productDTOList.add(ProductDTO.toProductDTO(product)); //productDTO 객체안에 DTO 데이터 넣음
        }
        return productDTOList;
    }

    @Override
    public List<ProductDTO> viewProductsBySale(){
        List<Product> productList = productRepository.findRandomProductsBySalePercentage();
        List<ProductDTO> productDTOList = new ArrayList<>();
        for(Product product: productList) {
            Optional<Double> opAvgRating = reviewRepository.getAverageRatingByProduct(product.getProductId()); //평균평점을 구함
            product.setRating(opAvgRating.orElse(0.0)); //평균평점을 구한것을 product테이블 rating 컬럼에 set함
        }
        productRepository.saveAll(productList); //다시 저장
        for(Product product: productList) {
            productDTOList.add(ProductDTO.toProductDTO(product)); //productDTO 객체안에 DTO 데이터 넣음
        }
        return productDTOList;
    }



    @Override
    public Page<ProductDTO> viewProducts(int pageNo, String arName){
        Page<Product> productList = null;
        Pageable pageable = arrangeService(arName, pageNo);
        productList = productRepository.findAll(pageable);
        combineRating(productList);
        Page<ProductDTO> productDTOList = productList.map(ProductDTO::toProductDTO); //productDTO 객체안에 DTO 데이터 넣음
        return productDTOList;
    }

    @Override
    public Page<ProductDTO> viewProductsByMainCategory(int pageNo, MainCategoryDTO mainCategoryDTO, String arName){
        Page<Product> productList = null;
        Pageable pageable = arrangeService(arName, pageNo);
        productList = productRepository.findAllByMainCategory(pageable, MainCategory.toMainCategoryEntity(mainCategoryDTO));
        combineRating(productList);
        Page<ProductDTO> productDTOList = productList.map(ProductDTO::toProductDTO); //productDTO 객체안에 DTO 데이터 넣음
        return productDTOList;
    }

    @Override
    public Page<ProductDTO>  viewProductsBySubCategory(int pageNo, MainCategoryDTO mainCategoryDTO,SubCategoryDTO subCategoryDTO,String arName){
        Page<Product> productList = null;
        Pageable pageable = arrangeService(arName, pageNo);
        productList = productRepository.findAllByMainCategoryAndSubCategory(pageable, MainCategory.toMainCategoryEntity(mainCategoryDTO), SubCategory.toSubCategoryEntity(subCategoryDTO));
        combineRating(productList);
        Page<ProductDTO> productDTOList = productList.map(ProductDTO::toProductDTO); //productDTO 객체안에 DTO 데이터 넣음
        return productDTOList;
    }

    @Override
    public Page<ProductDTO> viewProductsByMainMaterial(int pageNo, MainMaterialDTO mainMaterialDTO, String arName){
        Page<Product> productList = null;
        Pageable pageable = arrangeService(arName, pageNo);
        productList = productRepository.findAllByMainMaterial(pageable,MainMaterial.toMainMaterialEntity(mainMaterialDTO));
        combineRating(productList);
        Page<ProductDTO> productDTOList = productList.map(ProductDTO::toProductDTO); //productDTO 객체안에 DTO 데이터 넣음
        return productDTOList;
    }

    @Override
    public Page<ProductDTO> viewProductsByMcAndMm(int pageNo, MainCategoryDTO mainCategoryDTO, MainMaterialDTO mainMaterialDTO, String arName){
        Page<Product> productList = null;
        Pageable pageable = arrangeService(arName, pageNo);
        productList = productRepository.findAllByMainCategoryAndMainMaterial(pageable, MainCategory.toMainCategoryEntity(mainCategoryDTO),
                MainMaterial.toMainMaterialEntity(mainMaterialDTO));
        combineRating(productList);
        Page<ProductDTO> productDTOList = productList.map(ProductDTO::toProductDTO); //productDTO 객체안에 DTO 데이터 넣음
        return productDTOList;
    }

    @Override
    public Page<ProductDTO> viewProductsByMcAndScAndMm(int pageNo, MainCategoryDTO mainCategoryDTO, SubCategoryDTO subCategoryDTO, MainMaterialDTO mainMaterialDTO, String arName){
        Page<Product> productList = null;
        Pageable pageable = arrangeService(arName, pageNo);
        productList = productRepository.findAllByMainCategoryAndSubCategoryAndMainMaterial(pageable,MainCategory.toMainCategoryEntity(mainCategoryDTO),
                SubCategory.toSubCategoryEntity(subCategoryDTO),
                MainMaterial.toMainMaterialEntity(mainMaterialDTO));
        combineRating(productList);
        Page<ProductDTO> productDTOList = productList.map(ProductDTO::toProductDTO); //productDTO 객체안에 DTO 데이터 넣음
        return productDTOList;
    }

    @Override
    public Page<ProductDTO> viewProductsByEvent(int pageNo,EventDTO eventDTO, String arName){
        Page<Product> productList = null;
        Pageable pageable = arrangeService(arName, pageNo);
        productList = productRepository.findAllByEvent(pageable,Event.toEventEntity(eventDTO));
        combineRating(productList);
        Page<ProductDTO> productDTOList = productList.map(ProductDTO::toProductDTO); //productDTO 객체안에 DTO 데이터 넣음
        return productDTOList;
    }

    @Override
    public Page<ProductDTO> viewProductsByEventAndMainCategory(int pageNo,EventDTO eventDTO, MainCategoryDTO mainCategoryDTO, String arName){
        Page<Product> productList = null;
        Pageable pageable = arrangeService(arName, pageNo);
        productList = productRepository.findAllByEventAndMainCategory(pageable,Event.toEventEntity(eventDTO), MainCategory.toMainCategoryEntity(mainCategoryDTO));
        combineRating(productList);
        Page<ProductDTO> productDTOList = productList.map(ProductDTO::toProductDTO); //productDTO 객체안에 DTO 데이터 넣음
        return productDTOList;
    }

    @Override
    public Page<ProductDTO> viewProductsByEventAndMcAndSc(int pageNo,EventDTO eventDTO, MainCategoryDTO mainCategoryDTO, SubCategoryDTO subCategoryDTO, String arName){
        Page<Product> productList = null;
        Pageable pageable = arrangeService(arName, pageNo);
        productList = productRepository.findAllByEventAndMainCategoryAndSubCategory(pageable,Event.toEventEntity(eventDTO), MainCategory.toMainCategoryEntity(mainCategoryDTO),
                SubCategory.toSubCategoryEntity(subCategoryDTO));
        combineRating(productList);
        Page<ProductDTO> productDTOList = productList.map(ProductDTO::toProductDTO); //productDTO 객체안에 DTO 데이터 넣음
        return productDTOList;
    }

    @Override
    public Page<ProductDTO> viewProductsByEventAndMainMaterial(int pageNo,EventDTO eventDTO, MainMaterialDTO mainMaterialDTO, String arName){
        Page<Product> productList = null;
        Pageable pageable = arrangeService(arName, pageNo);
        productList = productRepository.findAllByEventAndMainMaterial(pageable,Event.toEventEntity(eventDTO), MainMaterial.toMainMaterialEntity(mainMaterialDTO));
        combineRating(productList);
        Page<ProductDTO> productDTOList = productList.map(ProductDTO::toProductDTO); //productDTO 객체안에 DTO 데이터 넣음
        return productDTOList;
    }
    @Override
    public Page<ProductDTO> viewProductsByEventAndMcAndMm(int pageNo,EventDTO eventDTO, MainCategoryDTO mainCategoryDTO, MainMaterialDTO mainMaterialDTO, String arName){
        Page<Product> productList = null;
        Pageable pageable = arrangeService(arName, pageNo);
        productList = productRepository.findAllByEventAndMainCategoryAndMainMaterial(pageable,Event.toEventEntity(eventDTO), MainCategory.toMainCategoryEntity(mainCategoryDTO),MainMaterial.toMainMaterialEntity(mainMaterialDTO));
        combineRating(productList);
        Page<ProductDTO> productDTOList = productList.map(ProductDTO::toProductDTO); //productDTO 객체안에 DTO 데이터 넣음
        return productDTOList;
    }
    @Override
    public Page<ProductDTO> viewProductsByEventAndMcAndScAndMm(int pageNo,EventDTO eventDTO, MainCategoryDTO mainCategoryDTO, SubCategoryDTO subCategoryDTO, MainMaterialDTO mainMaterialDTO, String arName) {
        Page<Product> productList = null;
        Pageable pageable = arrangeService(arName, pageNo);
        productList = productRepository.findAllByEventAndMainCategoryAndSubCategoryAndMainMaterial(pageable,Event.toEventEntity(eventDTO), MainCategory.toMainCategoryEntity(mainCategoryDTO),
                SubCategory.toSubCategoryEntity(subCategoryDTO), MainMaterial.toMainMaterialEntity(mainMaterialDTO));
        combineRating(productList);
        Page<ProductDTO> productDTOList = productList.map(ProductDTO::toProductDTO); //productDTO 객체안에 DTO 데이터 넣음
        return productDTOList;
    }


    @Override
    public ProductDTO viewDetailProduct(Long productId) {
        Optional<Product> opProduct = productRepository.findById(productId);
        ProductDTO productDTO = new ProductDTO();
        if (opProduct.isPresent()) {
            Product product = opProduct.get();
            productDTO = ProductDTO.toProductDTO(product);
            // findById로 받아온 결과값들을 DTO에 저장함

            return productDTO;
        } else {
            throw new IllegalArgumentException("해당 ID의 상품이 없습니다. ID: " + productDTO.getProductId());
        }

    }

    @Override
    public ProductDTO getProductById(Long productId) {
        Optional<Product> product = productRepository.findById(productId);
        if(product.isPresent()) {
            return ProductDTO.toProductDTO(product.get());
        } else {
            throw new IllegalArgumentException("Invalid product Id: " + productId);
        }
    }

    @Override
    public Page<ProductDTO> searchByProductName(int pageNo,String name, String arName){
        Page<Product> productList = null;
        Pageable pageable = arrangeService(arName, pageNo);
        productList = productRepository.findAllByNameContaining(pageable,name);
        combineRating(productList);
        Page<ProductDTO> productDTOList = productList.map(ProductDTO::toProductDTO); //productDTO 객체안에 DTO 데이터 넣음
        return productDTOList;
    }

    @Override
    public Page<ProductDTO> searchByMainCategoryName(int pageNo,String name, String arName) {
        Page<Product> productList = null;
        Pageable pageable = arrangeService(arName, pageNo);
        productList = productRepository.findAllByMainCategoryNameContaining(pageable,name);
        combineRating(productList);
        Page<ProductDTO> productDTOList = productList.map(ProductDTO::toProductDTO); //productDTO 객체안에 DTO 데이터 넣음
        return productDTOList;
    }

    @Override
    public Page<ProductDTO> searchBySubCategoryName(int pageNo,String name, String arName) {
        Page<Product> productList = null;
        Pageable pageable = arrangeService(arName, pageNo);
        productList = productRepository.findAllBySubCategoryNameContaining(pageable,name);
        combineRating(productList);
        Page<ProductDTO> productDTOList = productList.map(ProductDTO::toProductDTO); //productDTO 객체안에 DTO 데이터 넣음
        return productDTOList;
    }

    @Override
    public Page<ProductDTO> searchByMainMaterialName(int pageNo,String name, String arName) {
        Page<Product> productList = null;
        Pageable pageable = arrangeService(arName, pageNo);
        productList = productRepository.findAllByMainMaterialNameContaining(pageable,name);
        combineRating(productList);
        Page<ProductDTO> productDTOList = productList.map(ProductDTO::toProductDTO); //productDTO 객체안에 DTO 데이터 넣음
        return productDTOList;
    }

}