package idusw.leafton.model;

public class UploadFile {
    private String uploadFileName; // 사용자가 업로드한 파일명
    private String storeFileName; // 서부 내부에서 관리하는 파일명
    private String imagePath;

    public UploadFile(String uploadFileName, String storeFileName, String imagePath) {
        this.uploadFileName = uploadFileName;
        //this.storeFile  Name = storeFileName;
        this.imagePath = imagePath;
    }
}
