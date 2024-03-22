package idusw.leafton.model.service;

import idusw.leafton.model.DTO.MainCategoryDTO;
import idusw.leafton.model.DTO.ProductDTO;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface MainCategoryService {
    List<MainCategoryDTO> viewAllMainCategory();
    MainCategoryDTO getMainCategoryById(Long mainCategoryId);
    MainCategoryDTO getMainCategoryDetail(Long mainCategoryId);
    MainCategoryDTO insertAndUpdateMainCategory(MainCategoryDTO mainCategoryDTO, MultipartFile image) throws IOException;
}
