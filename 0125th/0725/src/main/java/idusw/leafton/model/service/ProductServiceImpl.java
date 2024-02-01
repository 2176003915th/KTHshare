package idusw.leafton.model.service;

import idusw.leafton.model.DTO.*;
import idusw.leafton.model.entity.*;
import idusw.leafton.model.repository.MainCategoryRepository;
import idusw.leafton.model.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import idusw.leafton.model.repository.ProductRepository;

import java.util.*;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService { //ProductService를 구현도를 받고 implements를 구현함

    private final ProductRepository productRepository; //객체의 상태를 저장하고, 해당 상태를 클래스 내의 여러 메서드에서 공유하거나 조작하기 위해 필드 선언
    private final ReviewRepository reviewRepository;

    @Override
    public List<ProductDTO> viewProducts(String arName){
        List<Product> productList = null;
        if (arName == null) {
            productList = productRepository.findAll();
        } else {
            switch (arName) {
                case "name":
                    productList = productRepository.findAll(Sort.by(Sort.Direction.ASC, "name"));
                    break;
                case "new":
                    productList = productRepository.findAll(Sort.by(Sort.Direction.ASC, "registDate"));
                    break;
                case "rating":
                    productList = productRepository.findAll(Sort.by(Sort.Direction.DESC, "rating"));
                    break;
                case "aprice":
                    productList = productRepository.findAll(Sort.by(Sort.Direction.ASC, "price"));
                    break;
                case "dprice":
                    productList = productRepository.findAll(Sort.by(Sort.Direction.DESC, "price"));
            };
        }

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
    public List<ProductDTO> viewProductsByMainCategory(MainCategoryDTO mainCategoryDTO){
        List<Product> productList = productRepository.findAllByMainCategory(MainCategory.toMainCategoryEntity(mainCategoryDTO), Sort.by(Sort.Direction.ASC, "name"));
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
    public List<ProductDTO> viewProductsByMainCategory(MainCategoryDTO mainCategoryDTO, String arName){
        List<Product> productList = null;
        if (arName == null) {
            productList = productRepository.findAllByMainCategory(MainCategory.toMainCategoryEntity(mainCategoryDTO), Sort.by(Sort.Direction.ASC, "name"));
        } else {
            switch (arName) {
                case "name":
                    productList = productRepository.findAllByMainCategory(MainCategory.toMainCategoryEntity(mainCategoryDTO), Sort.by(Sort.Direction.ASC, "name"));
                    break;
                case "new":
                    productList = productRepository.findAllByMainCategory(MainCategory.toMainCategoryEntity(mainCategoryDTO), Sort.by(Sort.Direction.ASC, "registDate"));
                    break;
                case "rating":
                    productList = productRepository.findAllByMainCategory(MainCategory.toMainCategoryEntity(mainCategoryDTO), Sort.by(Sort.Direction.DESC, "rating"));
                    break;
                case "aprice":
                    productList = productRepository.findAllByMainCategory(MainCategory.toMainCategoryEntity(mainCategoryDTO), Sort.by(Sort.Direction.ASC, "price"));
                    break;
                case "dprice":
                    productList = productRepository.findAllByMainCategory(MainCategory.toMainCategoryEntity(mainCategoryDTO), Sort.by(Sort.Direction.DESC, "price"));
            };
        }
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
    public List<ProductDTO>  viewProductsBySubCategory(MainCategoryDTO mainCategoryDTO,SubCategoryDTO subCategoryDTO,String arName){
        List<Product> productList = null;
        if (arName == null) {
            productList = productRepository.findAllByMainCategoryAndSubCategory(MainCategory.toMainCategoryEntity(mainCategoryDTO)
                    ,SubCategory.toSubCategoryEntity(subCategoryDTO)
                    ,Sort.by(Sort.Direction.ASC, "name"));
        } else {
            switch (arName) {
                case "name":
                    productList = productRepository.findAllByMainCategoryAndSubCategory(MainCategory.toMainCategoryEntity(mainCategoryDTO),
                            SubCategory.toSubCategoryEntity(subCategoryDTO), Sort.by(Sort.Direction.ASC, "name"));
                    break;
                case "new":
                    productList = productRepository.findAllByMainCategoryAndSubCategory(MainCategory.toMainCategoryEntity(mainCategoryDTO),
                            SubCategory.toSubCategoryEntity(subCategoryDTO), Sort.by(Sort.Direction.ASC, "registDate"));
                    break;
                case "rating":
                    productList = productRepository.findAllByMainCategoryAndSubCategory(MainCategory.toMainCategoryEntity(mainCategoryDTO),
                            SubCategory.toSubCategoryEntity(subCategoryDTO), Sort.by(Sort.Direction.DESC, "rating"));
                    break;
                case "aprice":
                    productList = productRepository.findAllByMainCategoryAndSubCategory(MainCategory.toMainCategoryEntity(mainCategoryDTO),
                            SubCategory.toSubCategoryEntity(subCategoryDTO), Sort.by(Sort.Direction.ASC, "price"));
                    break;
                case "dprice":
                    productList = productRepository.findAllByMainCategoryAndSubCategory(MainCategory.toMainCategoryEntity(mainCategoryDTO),
                            SubCategory.toSubCategoryEntity(subCategoryDTO), Sort.by(Sort.Direction.DESC, "price"));
            };
        }
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
    public List<ProductDTO> viewProductsByMainMaterial(MainMaterialDTO mainMaterialDTO, String arName){
        List<Product> productList = null;
        if (arName == null) {
            productList = productRepository.findAllByMainMaterial(MainMaterial.toMainMaterialEntity(mainMaterialDTO), Sort.by(Sort.Direction.ASC, "name"));
        } else {
            switch (arName) {
                case "name":
                    productList = productRepository.findAllByMainMaterial(MainMaterial.toMainMaterialEntity(mainMaterialDTO), Sort.by(Sort.Direction.ASC, "name"));
                    break;
                case "new":
                    productList = productRepository.findAllByMainMaterial(MainMaterial.toMainMaterialEntity(mainMaterialDTO), Sort.by(Sort.Direction.ASC, "registDate"));
                    break;
                case "rating":
                    productList = productRepository.findAllByMainMaterial(MainMaterial.toMainMaterialEntity(mainMaterialDTO), Sort.by(Sort.Direction.DESC, "rating"));
                    break;
                case "aprice":
                    productList = productRepository.findAllByMainMaterial(MainMaterial.toMainMaterialEntity(mainMaterialDTO), Sort.by(Sort.Direction.ASC, "price"));
                    break;
                case "dprice":
                    productList = productRepository.findAllByMainMaterial(MainMaterial.toMainMaterialEntity(mainMaterialDTO), Sort.by(Sort.Direction.DESC, "price"));
            };
        }
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
    public List<ProductDTO> viewProductsByMcAndMm(MainCategoryDTO mainCategoryDTO, MainMaterialDTO mainMaterialDTO, String arName){
        List<Product> productList = null;
        if (arName == null) {
            productList = productRepository.findAllByMainCategoryAndMainMaterial(MainCategory.toMainCategoryEntity(mainCategoryDTO),
                    MainMaterial.toMainMaterialEntity(mainMaterialDTO),Sort.by(Sort.Direction.ASC, "name"));
        } else {
            switch (arName) {
                case "name":
                    productList = productRepository.findAllByMainCategoryAndMainMaterial(MainCategory.toMainCategoryEntity(mainCategoryDTO),
                            MainMaterial.toMainMaterialEntity(mainMaterialDTO), Sort.by(Sort.Direction.ASC, "name"));
                    break;
                case "new":
                    productList = productRepository.findAllByMainCategoryAndMainMaterial(MainCategory.toMainCategoryEntity(mainCategoryDTO),
                            MainMaterial.toMainMaterialEntity(mainMaterialDTO), Sort.by(Sort.Direction.ASC, "registDate"));
                    break;
                case "rating":
                    productList = productRepository.findAllByMainCategoryAndMainMaterial(MainCategory.toMainCategoryEntity(mainCategoryDTO),
                            MainMaterial.toMainMaterialEntity(mainMaterialDTO), Sort.by(Sort.Direction.DESC, "rating"));
                    break;
                case "aprice":
                    productList = productRepository.findAllByMainCategoryAndMainMaterial(MainCategory.toMainCategoryEntity(mainCategoryDTO),
                            MainMaterial.toMainMaterialEntity(mainMaterialDTO), Sort.by(Sort.Direction.ASC, "price"));
                    break;
                case "dprice":
                    productList = productRepository.findAllByMainCategoryAndMainMaterial(MainCategory.toMainCategoryEntity(mainCategoryDTO),
                            MainMaterial.toMainMaterialEntity(mainMaterialDTO), Sort.by(Sort.Direction.DESC, "price"));
            };
        }
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
    public List<ProductDTO> viewProductsByMcAndScAndMm(MainCategoryDTO mainCategoryDTO, SubCategoryDTO subCategoryDTO, MainMaterialDTO mainMaterialDTO, String arName){
        List<Product> productList = null;
        if (arName == null) {
            productList = productRepository.findAllByMainCategoryAndSubCategoryAndMainMaterial(MainCategory.toMainCategoryEntity(mainCategoryDTO),
                    SubCategory.toSubCategoryEntity(subCategoryDTO),
                    MainMaterial.toMainMaterialEntity(mainMaterialDTO),
                    Sort.by(Sort.Direction.ASC, "name"));
        } else {
            switch (arName) {
                case "name":
                    productList = productRepository.findAllByMainCategoryAndSubCategoryAndMainMaterial(MainCategory.toMainCategoryEntity(mainCategoryDTO),
                            SubCategory.toSubCategoryEntity(subCategoryDTO), MainMaterial.toMainMaterialEntity(mainMaterialDTO), Sort.by(Sort.Direction.ASC, "name"));
                    break;
                case "new":
                    productList = productRepository.findAllByMainCategoryAndSubCategoryAndMainMaterial(MainCategory.toMainCategoryEntity(mainCategoryDTO),
                            SubCategory.toSubCategoryEntity(subCategoryDTO), MainMaterial.toMainMaterialEntity(mainMaterialDTO), Sort.by(Sort.Direction.ASC, "registDate"));
                    break;
                case "rating":
                    productList = productRepository.findAllByMainCategoryAndSubCategoryAndMainMaterial(MainCategory.toMainCategoryEntity(mainCategoryDTO),
                            SubCategory.toSubCategoryEntity(subCategoryDTO), MainMaterial.toMainMaterialEntity(mainMaterialDTO), Sort.by(Sort.Direction.DESC, "rating"));
                    break;
                case "aprice":
                    productList = productRepository.findAllByMainCategoryAndSubCategoryAndMainMaterial(MainCategory.toMainCategoryEntity(mainCategoryDTO),
                            SubCategory.toSubCategoryEntity(subCategoryDTO), MainMaterial.toMainMaterialEntity(mainMaterialDTO), Sort.by(Sort.Direction.ASC, "price"));
                    break;
                case "dprice":
                    productList = productRepository.findAllByMainCategoryAndSubCategoryAndMainMaterial(MainCategory.toMainCategoryEntity(mainCategoryDTO),
                            SubCategory.toSubCategoryEntity(subCategoryDTO), MainMaterial.toMainMaterialEntity(mainMaterialDTO), Sort.by(Sort.Direction.DESC, "price"));
            };
        }
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
    public List<ProductDTO> viewProductsByEvent(EventDTO eventDTO, String arName){
        List<Product> productList = null;
        if (arName == null) {
            productList = productRepository.findAllByEvent(Event.toEventEntity(eventDTO),
                    Sort.by(Sort.Direction.ASC, "name"));
        } else {
            switch (arName) {
                case "name":
                    productList = productRepository.findAllByEvent(Event.toEventEntity(eventDTO), Sort.by(Sort.Direction.ASC, "name"));
                    break;
                case "new":
                    productList = productRepository.findAllByEvent(Event.toEventEntity(eventDTO), Sort.by(Sort.Direction.ASC, "registDate"));
                    break;
                case "rating":
                    productList = productRepository.findAllByEvent(Event.toEventEntity(eventDTO), Sort.by(Sort.Direction.DESC, "rating"));
                    break;
                case "aprice":
                    productList = productRepository.findAllByEvent(Event.toEventEntity(eventDTO), Sort.by(Sort.Direction.ASC, "price"));
                    break;
                case "dprice":
                    productList = productRepository.findAllByEvent(Event.toEventEntity(eventDTO), Sort.by(Sort.Direction.DESC, "price"));
            };
        }
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
    public List<ProductDTO> viewProductsByEventAndMainCategory(EventDTO eventDTO, MainCategoryDTO mainCategoryDTO, String arName){
        List<Product> productList = null;
        if (arName == null) {
            productList = productRepository.findAllByEventAndMainCategory(Event.toEventEntity(eventDTO), MainCategory.toMainCategoryEntity(mainCategoryDTO),
                    Sort.by(Sort.Direction.ASC, "name"));
        } else {
            switch (arName) {
                case "name":
                    productList = productRepository.findAllByEventAndMainCategory(Event.toEventEntity(eventDTO), MainCategory.toMainCategoryEntity(mainCategoryDTO),
                            Sort.by(Sort.Direction.ASC, "name"));
                    break;
                case "new":
                    productList = productRepository.findAllByEventAndMainCategory(Event.toEventEntity(eventDTO), MainCategory.toMainCategoryEntity(mainCategoryDTO),
                            Sort.by(Sort.Direction.ASC,"registDate"));
                    break;
                case "rating":
                    productList = productRepository.findAllByEventAndMainCategory(Event.toEventEntity(eventDTO), MainCategory.toMainCategoryEntity(mainCategoryDTO),
                            Sort.by(Sort.Direction.DESC, "rating"));
                    break;
                case "aprice":
                    productList = productRepository.findAllByEventAndMainCategory(Event.toEventEntity(eventDTO), MainCategory.toMainCategoryEntity(mainCategoryDTO),
                            Sort.by(Sort.Direction.ASC, "price"));
                    break;
                case "dprice":
                    productList = productRepository.findAllByEventAndMainCategory(Event.toEventEntity(eventDTO), MainCategory.toMainCategoryEntity(mainCategoryDTO),
                            Sort.by(Sort.Direction.DESC, "price"));
            };
        }
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
    public List<ProductDTO> viewProductsByEventAndMcAndSc(EventDTO eventDTO, MainCategoryDTO mainCategoryDTO, SubCategoryDTO subCategoryDTO, String arName){
        List<Product> productList = null;
        if (arName == null) {
            productList = productRepository.findAllByEventAndMainCategoryAndSubCategory(Event.toEventEntity(eventDTO), MainCategory.toMainCategoryEntity(mainCategoryDTO),
                   SubCategory.toSubCategoryEntity(subCategoryDTO), Sort.by(Sort.Direction.ASC, "name"));
        } else {
            switch (arName) {
                case "name":
                    productList = productRepository.findAllByEventAndMainCategoryAndSubCategory(Event.toEventEntity(eventDTO), MainCategory.toMainCategoryEntity(mainCategoryDTO),
                            SubCategory.toSubCategoryEntity(subCategoryDTO), Sort.by(Sort.Direction.ASC, "name"));
                    break;
                case "new":
                    productList = productRepository.findAllByEventAndMainCategoryAndSubCategory(Event.toEventEntity(eventDTO), MainCategory.toMainCategoryEntity(mainCategoryDTO),
                            SubCategory.toSubCategoryEntity(subCategoryDTO), Sort.by(Sort.Direction.ASC,"registDate"));
                    break;
                case "rating":
                    productList = productRepository.findAllByEventAndMainCategoryAndSubCategory(Event.toEventEntity(eventDTO), MainCategory.toMainCategoryEntity(mainCategoryDTO),
                            SubCategory.toSubCategoryEntity(subCategoryDTO), Sort.by(Sort.Direction.DESC, "rating"));
                    break;
                case "aprice":
                    productList = productRepository.findAllByEventAndMainCategoryAndSubCategory(Event.toEventEntity(eventDTO), MainCategory.toMainCategoryEntity(mainCategoryDTO),
                            SubCategory.toSubCategoryEntity(subCategoryDTO), Sort.by(Sort.Direction.ASC, "price"));
                    break;
                case "dprice":
                    productList = productRepository.findAllByEventAndMainCategoryAndSubCategory(Event.toEventEntity(eventDTO), MainCategory.toMainCategoryEntity(mainCategoryDTO),
                            SubCategory.toSubCategoryEntity(subCategoryDTO), Sort.by(Sort.Direction.DESC, "price"));
            };
        }
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
    public List<ProductDTO> viewProductsByEventAndMainMaterial(EventDTO eventDTO, MainMaterialDTO mainMaterialDTO, String arName){
        List<Product> productList = null;
        if (arName == null) {
            productList = productRepository.findAllByEventAndMainMaterial(Event.toEventEntity(eventDTO), MainMaterial.toMainMaterialEntity(mainMaterialDTO),
                    Sort.by(Sort.Direction.ASC, "name"));
        } else {
            switch (arName) {
                case "name":
                    productList = productRepository.findAllByEventAndMainMaterial(Event.toEventEntity(eventDTO), MainMaterial.toMainMaterialEntity(mainMaterialDTO),  Sort.by(Sort.Direction.ASC, "name"));
                    break;
                case "new":
                    productList = productRepository.findAllByEventAndMainMaterial(Event.toEventEntity(eventDTO), MainMaterial.toMainMaterialEntity(mainMaterialDTO),  Sort.by(Sort.Direction.ASC,"registDate"));
                    break;
                case "rating":
                    productList = productRepository.findAllByEventAndMainMaterial(Event.toEventEntity(eventDTO), MainMaterial.toMainMaterialEntity(mainMaterialDTO),  Sort.by(Sort.Direction.DESC, "rating"));
                    break;
                case "aprice":
                    productList = productRepository.findAllByEventAndMainMaterial(Event.toEventEntity(eventDTO), MainMaterial.toMainMaterialEntity(mainMaterialDTO),  Sort.by(Sort.Direction.ASC, "price"));
                    break;
                case "dprice":
                    productList = productRepository.findAllByEventAndMainMaterial(Event.toEventEntity(eventDTO), MainMaterial.toMainMaterialEntity(mainMaterialDTO),  Sort.by(Sort.Direction.DESC, "price"));
            };
        }
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
    public List<ProductDTO> viewProductsByEventAndMcAndMm(EventDTO eventDTO, MainCategoryDTO mainCategoryDTO, MainMaterialDTO mainMaterialDTO, String arName){
        List<Product> productList = null;
        if (arName == null) {
            productList = productRepository.findAllByEventAndMainCategoryAndMainMaterial(Event.toEventEntity(eventDTO), MainCategory.toMainCategoryEntity(mainCategoryDTO),MainMaterial.toMainMaterialEntity(mainMaterialDTO),
                    Sort.by(Sort.Direction.ASC, "name"));
        } else {
            switch (arName) {
                case "name":
                    productList = productRepository.findAllByEventAndMainCategoryAndMainMaterial(Event.toEventEntity(eventDTO), MainCategory.toMainCategoryEntity(mainCategoryDTO),MainMaterial.toMainMaterialEntity(mainMaterialDTO),  Sort.by(Sort.Direction.ASC, "name"));
                    break;
                case "new":
                    productList = productRepository.findAllByEventAndMainCategoryAndMainMaterial(Event.toEventEntity(eventDTO), MainCategory.toMainCategoryEntity(mainCategoryDTO),MainMaterial.toMainMaterialEntity(mainMaterialDTO),  Sort.by(Sort.Direction.ASC,"registDate"));
                    break;
                case "rating":
                    productList = productRepository.findAllByEventAndMainCategoryAndMainMaterial(Event.toEventEntity(eventDTO), MainCategory.toMainCategoryEntity(mainCategoryDTO),MainMaterial.toMainMaterialEntity(mainMaterialDTO),  Sort.by(Sort.Direction.DESC, "rating"));
                    break;
                case "aprice":
                    productList = productRepository.findAllByEventAndMainCategoryAndMainMaterial(Event.toEventEntity(eventDTO), MainCategory.toMainCategoryEntity(mainCategoryDTO),MainMaterial.toMainMaterialEntity(mainMaterialDTO),  Sort.by(Sort.Direction.ASC, "price"));
                    break;
                case "dprice":
                    productList = productRepository.findAllByEventAndMainCategoryAndMainMaterial(Event.toEventEntity(eventDTO), MainCategory.toMainCategoryEntity(mainCategoryDTO),MainMaterial.toMainMaterialEntity(mainMaterialDTO),  Sort.by(Sort.Direction.DESC, "price"));
            };
        }
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
    public List<ProductDTO> viewProductsByEventAndMcAndScAndMm(EventDTO eventDTO, MainCategoryDTO mainCategoryDTO, SubCategoryDTO subCategoryDTO, MainMaterialDTO mainMaterialDTO, String arName) {
        List<Product> productList = null;
        if (arName == null) {
            productList = productRepository.findAllByEventAndMainCategoryAndSubCategoryAndMainMaterial(Event.toEventEntity(eventDTO), MainCategory.toMainCategoryEntity(mainCategoryDTO),
                    SubCategory.toSubCategoryEntity(subCategoryDTO), MainMaterial.toMainMaterialEntity(mainMaterialDTO), Sort.by(Sort.Direction.ASC, "name"));
        } else {
            switch (arName) {
                case "name":
                    productList = productRepository.findAllByEventAndMainCategoryAndSubCategoryAndMainMaterial(Event.toEventEntity(eventDTO), MainCategory.toMainCategoryEntity(mainCategoryDTO),
                            SubCategory.toSubCategoryEntity(subCategoryDTO), MainMaterial.toMainMaterialEntity(mainMaterialDTO), Sort.by(Sort.Direction.ASC, "name"));
                    break;
                case "new":
                    productList = productRepository.findAllByEventAndMainCategoryAndSubCategoryAndMainMaterial(Event.toEventEntity(eventDTO), MainCategory.toMainCategoryEntity(mainCategoryDTO),
                            SubCategory.toSubCategoryEntity(subCategoryDTO), MainMaterial.toMainMaterialEntity(mainMaterialDTO), Sort.by(Sort.Direction.ASC, "registDate"));
                    break;
                case "rating":
                    productList = productRepository.findAllByEventAndMainCategoryAndSubCategoryAndMainMaterial(Event.toEventEntity(eventDTO), MainCategory.toMainCategoryEntity(mainCategoryDTO),
                            SubCategory.toSubCategoryEntity(subCategoryDTO), MainMaterial.toMainMaterialEntity(mainMaterialDTO), Sort.by(Sort.Direction.DESC, "rating"));
                    break;
                case "aprice":
                    productList = productRepository.findAllByEventAndMainCategoryAndSubCategoryAndMainMaterial(Event.toEventEntity(eventDTO), MainCategory.toMainCategoryEntity(mainCategoryDTO),
                            SubCategory.toSubCategoryEntity(subCategoryDTO), MainMaterial.toMainMaterialEntity(mainMaterialDTO), Sort.by(Sort.Direction.ASC, "price"));
                    break;
                case "dprice":
                    productList = productRepository.findAllByEventAndMainCategoryAndSubCategoryAndMainMaterial(Event.toEventEntity(eventDTO), MainCategory.toMainCategoryEntity(mainCategoryDTO),
                            SubCategory.toSubCategoryEntity(subCategoryDTO), MainMaterial.toMainMaterialEntity(mainMaterialDTO), Sort.by(Sort.Direction.DESC, "price"));
            };
        }
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