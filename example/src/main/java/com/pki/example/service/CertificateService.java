package com.pki.example.service;

import com.pki.example.certificates.CertificateGenerator;
import com.pki.example.data.Issuer;
import com.pki.example.data.Subject;
import com.pki.example.dto.CertificateDto;
import com.pki.example.dto.CreateCertificateDto;
import com.pki.example.keystores.KeyStoreReader;
import com.pki.example.keystores.KeyStoreWriter;
import com.pki.example.util.CertificateUtils;
import org.bouncycastle.asn1.x500.X500NameBuilder;
import org.bouncycastle.asn1.x500.style.BCStyle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.security.*;
import java.security.cert.Certificate;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
public class CertificateService {

    @Autowired
    private KeyStoreReader keyStoreReader;
    @Autowired
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
                X509Certificate issuerCertificate = (X509Certificate) keyStoreReader.readCertificate("src/main/resources/static/example.jks", "password", createCertificateDto.getIssuerSerialNumber());
                PrivateKey privateKeyIssuer = keyStoreReader.readPrivateKey("src/main/resources/static/example.jks", "password", createCertificateDto.getIssuerSerialNumber(), "password");
                Random rand = new Random();
                String randomSerialNumber = (new BigInteger(32, rand)).toString();
                KeyPair keyPair = generateKeyPair();
                Subject subject = generateSubject(createCertificateDto, randomSerialNumber, keyPair.getPublic());
                Issuer issuer = generateIssuer(certificateUtils.X509CertificateToCertificateDto(issuerCertificate), createCertificateDto.getIssuerSerialNumber(), privateKeyIssuer);
                X509Certificate newCertificate = CertificateGenerator.generateCertificate(subject, issuer, createCertificateDto.getStartDate(), createCertificateDto.getEndDate(), randomSerialNumber);
                keyStoreWriter.loadKeyStore("src/main/resources/static/example.jks",  "password".toCharArray());
                keyStoreWriter.write(randomSerialNumber, privateKeyIssuer, "password".toCharArray(), newCertificate);
                keyStoreWriter.saveKeyStore("src/main/resources/static/example.jks",  "password".toCharArray());
                break;
            case "end_entity":
                break;
        }

        return null;
    }

    public Subject generateSubject(CreateCertificateDto certificateDTO, String serialNumber, PublicKey publicKey){
        X500NameBuilder x500NameBuilder = new X500NameBuilder(BCStyle.INSTANCE);
        x500NameBuilder.addRDN(BCStyle.CN, certificateDTO.getCommonNameSubject());
        x500NameBuilder.addRDN(BCStyle.NAME, certificateDTO.getNameSubject());
        x500NameBuilder.addRDN(BCStyle.SURNAME, certificateDTO.getSurnameSubject());
        x500NameBuilder.addRDN(BCStyle.UID, certificateDTO.getUsernameSubject());
        x500NameBuilder.addRDN(BCStyle.C, certificateDTO.getCountrySubject());
        x500NameBuilder.addRDN(BCStyle.SERIALNUMBER, serialNumber);

        return new Subject(publicKey, x500NameBuilder.build(), serialNumber);
    }

    public Issuer generateIssuer(CertificateDto certificateDTO, String serialNumber, PrivateKey privateKey){
        X500NameBuilder x500NameBuilder = new X500NameBuilder(BCStyle.INSTANCE);
        x500NameBuilder.addRDN(BCStyle.CN, certificateDTO.getCommonNameSubject() != null ? certificateDTO.getCommonNameSubject() : "");
        x500NameBuilder.addRDN(BCStyle.NAME, certificateDTO.getNameSubject() != null ? certificateDTO.getNameSubject() : "");
        x500NameBuilder.addRDN(BCStyle.SURNAME, certificateDTO.getSurnameSubject() != null ? certificateDTO.getSurnameSubject() : "");
        x500NameBuilder.addRDN(BCStyle.UID, certificateDTO.getUsernameSubject() != null ? certificateDTO.getUsernameSubject() : "");
        x500NameBuilder.addRDN(BCStyle.C, certificateDTO.getCountrySubject() != null ? certificateDTO.getCountrySubject() : "");
        x500NameBuilder.addRDN(BCStyle.SERIALNUMBER, serialNumber);

        return new Issuer(privateKey, serialNumber, x500NameBuilder.build());
    }

    private KeyPair generateKeyPair() {
        try {
            KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
            SecureRandom random = SecureRandom.getInstance("SHA1PRNG", "SUN");
            keyGen.initialize(2048, random);
            return keyGen.generateKeyPair();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchProviderException e) {
            e.printStackTrace();
        }
        return null;
    }
}
