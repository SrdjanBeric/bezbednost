package managementapp.managementapp.services;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Service
public class FileUploadService {

    public void uploadFile(MultipartFile file) throws IOException {
        try {
            File destFile = new File("C:\\"+file.getOriginalFilename());
            file.transferTo(destFile);
        } catch (IOException e) {
            throw new IOException("File not found!"+file.getOriginalFilename());
        }    }
}
