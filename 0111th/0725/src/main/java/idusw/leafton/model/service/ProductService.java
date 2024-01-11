package idusw.leafton.model.service;

import idusw.leafton.model.DTO.ProductDTO;


import java.util.List;

public interface ProductService { //ProductService 구현도
    public List<ProductDTO> viewAllProducts(); //List

    ProductDTO viewDetailProduct(Long productId);

    ProductDTO getProductDTOId(Long productId);
}