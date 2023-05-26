package managementapp.managementapp.controllers;

import managementapp.managementapp.services.FileUploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/file")
public class FileUploadController {

    @Autowired
    private FileUploadService fileUploadService;

    @PostMapping("/upload")
    @PreAuthorize("hasAnyAuthority('SOFTWARE_ENGINEER')")
    public void uploadFile(@RequestParam("file") MultipartFile file) throws IllegalStateException, IOException {
        System.out.println(file);
        fileUploadService.uploadFile(file);
    }

}
