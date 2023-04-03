package com.pki.example.service;

import com.pki.example.dto.CertificateDto;
import com.pki.example.dto.CreateCertificateDto;
import com.pki.example.keystores.KeyStoreReader;
import com.pki.example.keystores.KeyStoreWriter;
import com.pki.example.util.CertificateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;

@Service
public class CertificateService {

    @Autowired
    private KeyStoreReader keyStoreReader;
    private KeyStoreWriter keyStoreWriter;
    @Autowired
    private CertificateUtils certificateUtils;

    public List<CertificateDto> getAllCertificates(){
        List<X509Certificate> certificates = keyStoreReader.getAllCertificates();
        List<CertificateDto> certificateDtos = new ArrayList<>();
        for (X509Certificate cert: certificates) {
            certificateDtos.add(certificateUtils.X509CertificateToCertificateDto(cert));
        }
        return certificateDtos;
    }

    public CertificateDto createCertificate(CreateCertificateDto createCertificateDto){
        switch(createCertificateDto.getAuthoritySubject()){
            case "root":
                break;
            case "ca":
                keyStoreReader.readCertificate("src/main/resources/static/example.jks", "password", createCertificateDto.getIssuerSerialNumber());
                break;
            case "end_entity":
                break;
        }

        return null;
    }
}
