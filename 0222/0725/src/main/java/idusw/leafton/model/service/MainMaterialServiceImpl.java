package idusw.leafton.model.service;

import idusw.leafton.model.DTO.MainCategoryDTO;
import idusw.leafton.model.DTO.MainMaterialDTO;
import idusw.leafton.model.DTO.SubCategoryDTO;
import idusw.leafton.model.entity.MainCategory;
import idusw.leafton.model.entity.MainMaterial;
import idusw.leafton.model.entity.SubCategory;
import idusw.leafton.model.repository.MainMaterialRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MainMaterialServiceImpl implements MainMaterialService{
    private final MainMaterialRepository mainMaterialRepository;

    @Override
    public List<MainMaterialDTO>viewAllMainMaterial(){
        List<MainMaterial> mainMaterialList = mainMaterialRepository.findAll(); //레파지토리에서 메인재료메뉴 모두불러와서 list 엔티티 리스트에넣음
        List<MainMaterialDTO> mainMaterialDTOList = new ArrayList<>(); //dto메뉴 리스트 객체생성
        for(MainMaterial mainMaterial: mainMaterialList) { // 메인재료 엔티티 리스트를 메인재료 메뉴 dto에 넣음
            mainMaterialDTOList.add(MainMaterialDTO.toMainMaterialDTO(mainMaterial));
        }
        return mainMaterialDTOList;
    }

    @Override
    public MainMaterialDTO getMainMaterialDetail(Long mainMaterialId){
        Optional<MainMaterial> opMainMaterial = mainMaterialRepository.findById(mainMaterialId);
        MainMaterialDTO mainMaterialDTO = new MainMaterialDTO();
        if (opMainMaterial.isPresent()) {
            MainMaterial mainMaterial = opMainMaterial.get();
            mainMaterialDTO = MainMaterialDTO.toMainMaterialDTO(mainMaterial);
            // findById로 받아온 결과값들을 DTO에 저장함

            return mainMaterialDTO;
        } else {
            throw new IllegalArgumentException("해당 ID의 상품이 없습니다. ID: " + mainMaterialDTO.getMainMaterialId());
        }
    }
    @Override
    public MainMaterialDTO getMainMaterialById(Long mainMaterialId){

        Optional<MainMaterial> opMainMaterial = mainMaterialRepository.findById(mainMaterialId);
        MainMaterialDTO mainMaterialDTO = new MainMaterialDTO();
        if (opMainMaterial.isPresent()) {
            return MainMaterialDTO.toMainMaterialDTO(opMainMaterial.get());
        } else {
            throw new IllegalArgumentException("해당 ID의 상품이 없습니다. ID: " + mainMaterialDTO.getMainMaterialId());
        }
    }

    @Override
    public MainMaterialDTO insertAndUpdateMainMaterial(MainMaterialDTO mainMaterialDTO){
        MainMaterial mainMaterial = MainMaterial.toMainMaterialEntity(mainMaterialDTO); // entity에 넣기위하여 변경
        MainMaterial result = mainMaterialRepository.save(mainMaterial); // 레파지토리에서 save(insert)한 결과
        return MainMaterialDTO.toMainMaterialDTO(result); //결과를 dto에 저장
    }
}
