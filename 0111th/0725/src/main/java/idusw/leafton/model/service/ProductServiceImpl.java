package idusw.leafton.model.service;

import idusw.leafton.model.DTO.*;
import idusw.leafton.model.entity.MainCategory;
import idusw.leafton.model.entity.MainMaterial;
import idusw.leafton.model.entity.SubCategory;
import idusw.leafton.model.repository.MainCategoryRepository;
import idusw.leafton.model.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import idusw.leafton.model.entity.Product;
import idusw.leafton.model.repository.ProductRepository;

import java.util.*;

@Service
@RequiredArgsConstructor
public  class ProductServiceImpl implements ProductService { //ProductService를 구현도를 받고 implements를 구현함

    private final ProductRepository productRepository; //객체의 상태를 저장하고, 해당 상태를 클래스 내의 여러 메서드에서 공유하거나 조작하기 위해 필드 선언
    private final ReviewRepository reviewRepository;
    @Override
    public List<ProductDTO> viewAllProducts() { // 그래서 재정의함

        List<Product> productList = productRepository.findAll();
        List<ProductDTO> productDTOList = new ArrayList<>(); //productDTO로 ArrayList 객체만듬
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
    public List<ProductDTO> viewProductsByMainCategory(MainCategoryDTO mainCategoryDTO) {
        List<Product> productList = productRepository.findAllByMainCategory(MainCategory.toMainCategoryEntity(mainCategoryDTO));
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
    public List<ProductDTO> viewProductsBySubCategory(MainCategoryDTO mainCategoryDTO,SubCategoryDTO subCategoryDTO) {
        List<Product> productList = productRepository.findAllByMainCategoryAndSubCategory(MainCategory.toMainCategoryEntity(mainCategoryDTO)
                                                                            ,SubCategory.toSubCategoryEntity(subCategoryDTO));
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
    public List<ProductDTO> viewProductsByMainMaterial(MainMaterialDTO mainMaterialDTO){
        List<Product> productList = productRepository.findAllByMainMaterial(MainMaterial.toMainMaterialEntity(mainMaterialDTO));
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
    public List<ProductDTO> viewProductsByMcAndMm(MainCategoryDTO mainCategoryDTO, MainMaterialDTO mainMaterialDTO){
        List<Product> productList = productRepository.findAllByMainCategoryAndMainMaterial(MainCategory.toMainCategoryEntity(mainCategoryDTO),
                                                                                        MainMaterial.toMainMaterialEntity(mainMaterialDTO));
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
    public List<ProductDTO> viewProductsByMcAndScAndMm(MainCategoryDTO mainCategoryDTO, SubCategoryDTO subCategoryDTO, MainMaterialDTO mainMaterialDTO){
        List<Product> productList = productRepository.findAllByMainCategoryAndSubCategoryAndMainMaterial(MainCategory.toMainCategoryEntity(mainCategoryDTO),
                                                                                                        SubCategory.toSubCategoryEntity(subCategoryDTO),
                                                                                                        MainMaterial.toMainMaterialEntity(mainMaterialDTO));
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
    public List<ProductDTO> viewProductsOrderBy(String arName){
         List<Product> productList = null;
         switch (arName){
            case "name": productList = productRepository.findAllByOrderByNameAsc();
                break;
            case "new": productList = productRepository.findAllByOrderByRegistDateAsc();
                break;
            case "rating": productList = productRepository.findAllByOrderByRatingDesc();
                break;
            case "aprice": productList = productRepository.findAllByOrderByPriceAsc();
                break;
            case "dprice": productList = productRepository.findAllByOrderByPriceDesc();
        };
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
    public List<ProductDTO> viewProductsByMainCategoryByOrderBy(MainCategoryDTO mainCategoryDTO, String arName){
        List<Product> productList = null;
        switch (arName){
            case "name": productList = productRepository.findAllByMainCategoryOrderByNameAsc(MainCategory.toMainCategoryEntity(mainCategoryDTO));
                break;
            case "new": productList = productRepository.findAllByMainCategoryOrderByRegistDateAsc(MainCategory.toMainCategoryEntity(mainCategoryDTO));
                break;
            case "rating": productList = productRepository.findAllByMainCategoryOrderByRatingDesc(MainCategory.toMainCategoryEntity(mainCategoryDTO));
                break;
            case "aprice": productList = productRepository.findAllByMainCategoryOrderByPriceAsc(MainCategory.toMainCategoryEntity(mainCategoryDTO));
                break;
            case "dprice": productList = productRepository.findAllByMainCategoryOrderByPriceDesc(MainCategory.toMainCategoryEntity(mainCategoryDTO));
        };
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
    public List<ProductDTO>  viewProductsBySubCategoryOrderBy(MainCategoryDTO mainCategoryDTO,SubCategoryDTO subCategoryDTO,String arName){
        List<Product> productList = null;
        switch (arName){
            case "name": productList = productRepository.findAllByMainCategoryAndSubCategoryOrderByNameAsc(MainCategory.toMainCategoryEntity(mainCategoryDTO),
                    SubCategory.toSubCategoryEntity(subCategoryDTO));
                break;
            case "new": productList = productRepository.findAllByMainCategoryAndSubCategoryOrderByRegistDateAsc(MainCategory.toMainCategoryEntity(mainCategoryDTO),
                    SubCategory.toSubCategoryEntity(subCategoryDTO));
                break;
            case "rating": productList = productRepository.findAllByMainCategoryAndSubCategoryOrderByRatingDesc(MainCategory.toMainCategoryEntity(mainCategoryDTO),
                    SubCategory.toSubCategoryEntity(subCategoryDTO));
                break;
            case "aprice": productList = productRepository.findAllByMainCategoryAndSubCategoryOrderByPriceAsc(MainCategory.toMainCategoryEntity(mainCategoryDTO),
                    SubCategory.toSubCategoryEntity(subCategoryDTO));
                break;
            case "dprice": productList = productRepository.findAllByMainCategoryAndSubCategoryOrderByPriceDesc(MainCategory.toMainCategoryEntity(mainCategoryDTO),
                    SubCategory.toSubCategoryEntity(subCategoryDTO));
        };
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
    public List<ProductDTO> viewProductsByMainMaterialOrderBy(MainMaterialDTO mainMaterialDTO, String arName){
        List<Product> productList = null;
        switch (arName){
            case "name": productList = productRepository.findAllByMainMaterialOrderByNameAsc(MainMaterial.toMainMaterialEntity(mainMaterialDTO));
                break;
            case "new": productList = productRepository.findAllByMainMaterialOrderByRegistDateAsc(MainMaterial.toMainMaterialEntity(mainMaterialDTO));
                break;
            case "rating": productList = productRepository.findAllByMainMaterialOrderByRatingDesc(MainMaterial.toMainMaterialEntity(mainMaterialDTO));
                break;
            case "aprice": productList = productRepository.findAllByMainMaterialOrderByPriceAsc(MainMaterial.toMainMaterialEntity(mainMaterialDTO));
                break;
            case "dprice": productList = productRepository.findAllByMainMaterialOrderByPriceDesc(MainMaterial.toMainMaterialEntity(mainMaterialDTO));
        };
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
    public List<ProductDTO> viewProductsByMcAndMmOrderBy(MainCategoryDTO mainCategoryDTO, MainMaterialDTO mainMaterialDTO, String arName){
        List<Product> productList = null;
        switch (arName){
            case "name": productList = productRepository.findAllByMainCategoryAndMainMaterialOrderByNameAsc(MainCategory.toMainCategoryEntity(mainCategoryDTO),
                    MainMaterial.toMainMaterialEntity(mainMaterialDTO));
                break;
            case "new": productList = productRepository.findAllByMainCategoryAndMainMaterialOrderByRegistDateAsc(MainCategory.toMainCategoryEntity(mainCategoryDTO),
                    MainMaterial.toMainMaterialEntity(mainMaterialDTO));
                break;
            case "rating": productList = productRepository.findAllByMainCategoryAndMainMaterialOrderByRatingDesc(MainCategory.toMainCategoryEntity(mainCategoryDTO),
                    MainMaterial.toMainMaterialEntity(mainMaterialDTO));
                break;
            case "aprice": productList = productRepository.findAllByMainCategoryAndMainMaterialOrderByPriceAsc(MainCategory.toMainCategoryEntity(mainCategoryDTO),
                    MainMaterial.toMainMaterialEntity(mainMaterialDTO));
                break;
            case "dprice": productList = productRepository.findAllByMainCategoryAndMainMaterialOrderByPriceDesc(MainCategory.toMainCategoryEntity(mainCategoryDTO),
                    MainMaterial.toMainMaterialEntity(mainMaterialDTO));
        };
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
    public List<ProductDTO> viewProductsByMcAndScAndMmOrderBy(MainCategoryDTO mainCategoryDTO, SubCategoryDTO subCategoryDTO, MainMaterialDTO mainMaterialDTO, String arName){
        List<Product> productList = null;
        switch (arName){
            case "name": productList = productRepository.findAllByMainCategoryAndSubCategoryAndMainMaterialOrderByNameAsc(MainCategory.toMainCategoryEntity(mainCategoryDTO),
                    SubCategory.toSubCategoryEntity(subCategoryDTO),MainMaterial.toMainMaterialEntity(mainMaterialDTO));
                break;
            case "new": productList = productRepository.findAllByMainCategoryAndSubCategoryAndMainMaterialOrderByRegistDateAsc(MainCategory.toMainCategoryEntity(mainCategoryDTO),
                    SubCategory.toSubCategoryEntity(subCategoryDTO),MainMaterial.toMainMaterialEntity(mainMaterialDTO));
                break;
            case "rating": productList = productRepository.findAllByMainCategoryAndSubCategoryAndMainMaterialOrderByRatingDesc(MainCategory.toMainCategoryEntity(mainCategoryDTO),
                    SubCategory.toSubCategoryEntity(subCategoryDTO), MainMaterial.toMainMaterialEntity(mainMaterialDTO));
                break;
            case "aprice": productList = productRepository.findAllByMainCategoryAndSubCategoryAndMainMaterialOrderByPriceAsc(MainCategory.toMainCategoryEntity(mainCategoryDTO),
                    SubCategory.toSubCategoryEntity(subCategoryDTO),MainMaterial.toMainMaterialEntity(mainMaterialDTO));
                break;
            case "dprice": productList = productRepository.findAllByMainCategoryAndSubCategoryAndMainMaterialOrderByPriceDesc(MainCategory.toMainCategoryEntity(mainCategoryDTO),
                    SubCategory.toSubCategoryEntity(subCategoryDTO),MainMaterial.toMainMaterialEntity(mainMaterialDTO));
        };
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
    public ProductDTO viewDetailProduct(Long productId) {
        Optional<Product> opProduct = productRepository.findById(productId);
        ProductDTO productDTO = new ProductDTO();
        if (opProduct.isPresent()) {
            Product product = opProduct.get();

            productDTO.setProductId(product.getProductId());
            productDTO.setName(product.getName());
            productDTO.setPrice(product.getPrice());
            productDTO.setContent(product.getContent());
            productDTO.setSalePercentage(product.getSalePercentage());
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

}