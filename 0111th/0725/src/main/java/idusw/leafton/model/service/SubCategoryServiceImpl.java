package idusw.leafton.model.service;

import idusw.leafton.model.DTO.ReviewDTO;
import idusw.leafton.model.DTO.SubCategoryDTO;
import idusw.leafton.model.entity.Product;
import idusw.leafton.model.entity.Review;
import idusw.leafton.model.entity.SubCategory;
import idusw.leafton.model.repository.SubCategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SubCategoryServiceImpl implements SubCategoryService{
    private final SubCategoryRepository subCategoryRepository;
    @Override
    public List<SubCategoryDTO> getSubCategoryByMainCategoryId(Long mainCategoryId){
        List<SubCategory> subCategoryList = subCategoryRepository.findByMainCategoryId(mainCategoryId);
        List<SubCategoryDTO> subCategoryDTOList = new ArrayList<>();
        for(SubCategory subCategory: subCategoryList) {
            subCategoryDTOList.add(SubCategoryDTO.toSubCategoryDTO(subCategory));  //dto리스트에 reviwe entity 리스트 추가
        }
        return subCategoryDTOList;
    }
}
