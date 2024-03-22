package idusw.leafton.model.service;

import idusw.leafton.model.DTO.MainCategoryDTO;
import idusw.leafton.model.DTO.ProductDTO;
import idusw.leafton.model.DTO.ReviewDTO;
import idusw.leafton.model.FileSave;
import idusw.leafton.model.entity.MainCategory;
import idusw.leafton.model.entity.Product;
import idusw.leafton.model.entity.Review;
import idusw.leafton.model.repository.MainCategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MainCategoryServiceImpl implements MainCategoryService{
   private final MainCategoryRepository mainCategoryRepository;
   private final FileSave fileSave;
   @Override
   public List<MainCategoryDTO> viewAllMainCategory() { // 그래서 재정의함

       List<MainCategory> mainCategoryList = mainCategoryRepository.findAll();
       List<MainCategoryDTO> mainCategoryDTOList = new ArrayList<>(); //productDTO로 ArrayList 객체만듬
       for(MainCategory mainCategory: mainCategoryList) {
           mainCategoryDTOList.add(MainCategoryDTO.toMainCategoryDTO(mainCategory)); //productDTO 객체안에 DTO 데이터 넣음
       }
       return mainCategoryDTOList;
   }

   @Override
   public MainCategoryDTO getMainCategoryDetail(Long mainCategoryId) {
       Optional<MainCategory> opMainCategory = mainCategoryRepository.findById(mainCategoryId);
       MainCategoryDTO mainCategoryDTO = new MainCategoryDTO();
       if (opMainCategory.isPresent()) {
           MainCategory mainCategory = opMainCategory.get();
           mainCategoryDTO = MainCategoryDTO.toMainCategoryDTO(mainCategory);
           // findById로 받아온 결과값들을 DTO에 저장함

           return mainCategoryDTO;
       } else {
           throw new IllegalArgumentException("해당 ID의 상품이 없습니다. ID: " + mainCategoryDTO.getMainCategoryId());
       }
   }

   @Override
   public MainCategoryDTO getMainCategoryById(Long mainCategoryId) {
       Optional<MainCategory> mainCategory = mainCategoryRepository.findById(mainCategoryId);
       if(mainCategory.isPresent()) {
           return MainCategoryDTO.toMainCategoryDTO(mainCategory.get());
       } else {
           throw new IllegalArgumentException("Invalid mainCategory Id: " + mainCategoryId);
       }
   }

    @Override
    public MainCategoryDTO insertAndUpdateMainCategory(MainCategoryDTO mainCategoryDTO, MultipartFile image) throws IOException {
//        String mcSaveLocation = "/home/passion/images/main-category/"; //실제 저장 위치
        String mcSaveLocation = fileSave.getDefaultPath() + "main-category/"; //실제 저장 위치

        if (!image.isEmpty()) {
            String mcFilename = fileSave.saveFileAndRename(image,mcSaveLocation);
            mainCategoryDTO.setImage("/home/passion/images/main-category/" + mcFilename); // 파일의 저장된 이름을 설정
        }
        MainCategory mainCategory = MainCategory.toMainCategoryEntity(mainCategoryDTO); // entity에 넣기위하여 변경
        MainCategory result = mainCategoryRepository.save(mainCategory); // 레파지토리에서 save(insert)한 결과
        return MainCategoryDTO.toMainCategoryDTO(result); //결과를 dto에 저장
    }
}
