package idusw.leafton.model;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Component
public class FileSave {


    public String savePath(String filename, String location) {
        return location + filename;
    }

    public String saveFileAndRename(MultipartFile multipartFile, String location) throws IOException{ //파일을 저장시키고 변경된 파일이름을 리턴
        if (multipartFile.isEmpty()){
            return null;
        }

        String originalFilename = multipartFile.getOriginalFilename(); // 받은 이미지 파일 원본 이름
        String storeFileName = createStoreFileName(originalFilename); // 서버내부에서 파일이름을 다르게하기위해 메서드적용
        multipartFile.transferTo(new File(savePath(storeFileName, location))); //파일저장
        return storeFileName;
    }

    public String createStoreFileName(String originalFilename) {
        String ext = extractExt(originalFilename);
        String uuid = UUID.randomUUID().toString();
        return uuid + "." + ext;
    }

    private String extractExt(String originalFilename) {
        int pos = originalFilename.lastIndexOf(".");
        return originalFilename.substring(pos + 1);
    }

}
