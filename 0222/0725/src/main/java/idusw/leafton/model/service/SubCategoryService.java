package idusw.leafton.model.service;

import idusw.leafton.model.DTO.MainCategoryDTO;
import idusw.leafton.model.DTO.SubCategoryDTO;

import java.util.List;

public interface SubCategoryService {
    List<SubCategoryDTO> getSubCategoryByMainCategoryId(MainCategoryDTO mainCategoryDTO); //메인카테고리 선택시 서브 카테고리 목록 표시
    List<SubCategoryDTO> viewAllSubCategory();
    SubCategoryDTO getSubCategoryById(Long subCategoryId);
    SubCategoryDTO getSubCategoryDetail(Long subCategoryId);
    SubCategoryDTO insertAndUpdateSubCategory(SubCategoryDTO subCategoryDTO);
}
