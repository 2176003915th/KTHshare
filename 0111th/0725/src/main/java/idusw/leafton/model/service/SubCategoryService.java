package idusw.leafton.model.service;

import idusw.leafton.model.DTO.SubCategoryDTO;

import java.util.List;

public interface SubCategoryService {
    List<SubCategoryDTO> getSubCategoryByMainCategoryId(Long mainCategoryId);
}
