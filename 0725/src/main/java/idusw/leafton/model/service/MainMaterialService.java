package idusw.leafton.model.service;

import idusw.leafton.model.DTO.MainMaterialDTO;
import idusw.leafton.model.entity.MainMaterial;

import java.util.List;

public interface MainMaterialService {
    List<MainMaterialDTO> viewAllMainMaterial(); // 메인 재료 메뉴 조회
    MainMaterialDTO getMainMaterialById(Long mainMaterialId);
    MainMaterialDTO getMainMaterialDetail(Long mainMaterialId); //선택한 메인재료메뉴를 기존메뉴에 표시하기위해 이름조회
    MainMaterialDTO insertAndUpdateMainMaterial(MainMaterialDTO mainMaterialDTO);
}
