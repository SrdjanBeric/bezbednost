package com.pki.example.controller;

import com.pki.example.dto.CertificateDto;
import com.pki.example.dto.CreateCertificateDto;
import com.pki.example.models.CertificateApp;
import com.pki.example.models.UserApp;
import com.pki.example.service.CertificateService;
import com.pki.example.service.UserAppService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

import javax.validation.Valid;
import java.security.cert.Certificate;
import java.security.cert.X509Certificate;
import java.util.List;

@RestController
@RequestMapping("/certificate")
public class CertificateController {
    @Autowired
    private CertificateService certificateService;

    @Autowired
    private UserAppService userAppService;

    @GetMapping("/all")
    @PreAuthorize("hasAuthority('ADMIN')")
    public List<CertificateDto> getAllCertificates(){
        return certificateService.getAllCertificates();
    }

    @GetMapping("/allRoot")
    @PreAuthorize("hasAuthority('ADMIN')")
    public List<CertificateDto> getAllRootCertificates(){
        return certificateService.getAllRootCertificates();
    }

    @GetMapping("/allCa")
    @PreAuthorize("hasAuthority('ADMIN')")
    public List<CertificateDto> getAllCaCertificates(){
        return certificateService.getAllCaCertificates();
    }

    @GetMapping("/allEe")
    @PreAuthorize("hasAuthority('ADMIN')")
    public List<CertificateDto> getAllEeCertificates(){
        return certificateService.getAllEeCertificates();
    }

    @GetMapping("/myCertificates")
    public ResponseEntity<List<CertificateDto>> getMyCertificates(){
        UserApp loggedInUser = userAppService.FindByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
        List<CertificateDto> certificateAppList = certificateService.getMyCertificates(loggedInUser);
        return new ResponseEntity<>(certificateAppList, HttpStatus.OK);

    }

    @PostMapping("/create")
    @PreAuthorize("!hasAuthority('END_ENTITY')")
    public ResponseEntity<CertificateDto> createCertificate(@Valid @RequestBody CreateCertificateDto createCertificateDto){
        UserApp loggedInUser = userAppService.FindByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
        UserApp certificateOwner = userAppService.FindByUsername(createCertificateDto.getOwnerUsername());
        if(certificateOwner == null){
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }

        // CA je pokusao da izda ADMINU
        if(loggedInUser.getRole().getName().toString().equals("INTERMEDIARY") &&
            certificateOwner.getRole().getName().toString().equals("ADMIN")
        ){
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
        // CA je pokusao da izda Root sertifikat
        if(loggedInUser.getRole().getName().toString().equals("INTERMEDIARY") &&
                createCertificateDto.getAuthoritySubject().equals("root")){
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
        CertificateDto certificateDto = certificateService.createCertificate(createCertificateDto, certificateOwner);
        if(certificateDto == null){
            return null;
        }
        return new ResponseEntity<>(certificateDto, HttpStatus.OK);

    }
    @PostMapping("/checkExpired")
    public boolean checkExpired(@Valid @RequestBody CreateCertificateDto createCertificateDto){
        boolean expired = certificateService.checkCertificateExpired(createCertificateDto);
        return expired;
    }
    @PostMapping("/checkRevoked")
    public boolean isCertificateRevoked(@Valid @RequestBody CertificateDto certDto){
        UserApp loggedInUser = userAppService.FindByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
        boolean revoked = certificateService.isCertificateRevoked(loggedInUser,certDto);
        return revoked;
    }
    @PostMapping("/revoke")
    public ResponseEntity<List<CertificateDto>> revoke(@Valid @RequestBody CertificateDto certDto){
        UserApp loggedInUser = userAppService.FindByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
        certificateService.revokeCertificate(loggedInUser,certDto);
        return getMyCertificates();
    }
}
