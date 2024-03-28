package idusw.leafton.model;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Component
@Getter
public class FileSave {
    private String defaultPath="/home/passion/images/";
    public String saveFileAndRename(MultipartFile multipartFile, String location) throws IOException{ //파일을 저장시키고 변경된 파일이름을 리턴
        if (multipartFile.isEmpty()){
            return null;
        }
        String originalFilename = multipartFile.getOriginalFilename(); // 받은 이미지 파일 원본 이름
        String storeFileName = createStoreFileName(originalFilename); // 서버내부에서 파일이름을 다르게하기위해 메서드적용
        File file = new File(location, storeFileName);   //resource.getFile 메소드 사용시
        multipartFile.transferTo(file); //파일저장
        return storeFileName;
    }

    public String createStoreFileName(String originalFilename) {
        String ext = extractExt(originalFilename);
        String uuid = UUID.randomUUID().toString(); //랜덤 uid생성하여 toString으로 변환
        return uuid + "." + ext;
    }

    private String extractExt(String originalFilename) {
        int pos = originalFilename.lastIndexOf("."); //파일 확장자 부분찾기 . 다음 인덱스 찾음
        return originalFilename.substring(pos + 1); //찾은 pos 다음 위치부터 끝까지 잘라내어 반환함 만약 파일이름이 사진.png면 png반환
    }

}
