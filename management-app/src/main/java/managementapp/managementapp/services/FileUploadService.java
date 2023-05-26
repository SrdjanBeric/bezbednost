package managementapp.managementapp.services;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Service
public class FileUploadService {

    public void uploadFile(MultipartFile file) throws IOException {
        file.transferTo(new File("C:\\Users\\Korisnik\\Desktop\\bezbednost\\bezbednost\\management-app\\FolderUpload\\" + file.getOriginalFilename()));
    }
}
