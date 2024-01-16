package idusw.leafton.model.service;

import idusw.leafton.model.DTO.MainCategoryDTO;
import idusw.leafton.model.DTO.ProductDTO;

import java.util.List;

public interface MainCategoryService {
    List<MainCategoryDTO> viewAllMainCategory();
    MainCategoryDTO getMainCategoryById(Long mainCategoryId);
    MainCategoryDTO getMainCategoryName(Long mainCategoryId);
}
