package idusw.leafton.model.service;

import idusw.leafton.model.DTO.ProductDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import idusw.leafton.model.entity.Product;
import idusw.leafton.model.repository.ProductRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService { //ProductService를 구현도를 받고 implements를 구현함

    private final ProductRepository productRepository; //객체의 상태를 저장하고, 해당 상태를 클래스 내의 여러 메서드에서 공유하거나 조작하기 위해 필드 선언

    @Override
    public List<ProductDTO> viewAllProducts() { // 그래서 재정의함

        List<Product> productList = productRepository.findAll();
        List<ProductDTO> productDTOList = new ArrayList<>(); //productDTO로 ArrayList 객체만듬
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
    public ProductDTO getProductDTOId(Long productId) {
        Optional<Product> product = productRepository.findById(productId);
        if(product.isPresent()) {
            return ProductDTO.toProductDTO(product.get());
        } else {
            throw new IllegalArgumentException("Invalid product Id: " + productId);
        }
    }

}