package com.pki.example.service;

import com.pki.example.certificates.CertificateGenerator;
import com.pki.example.data.Certificate;
import com.pki.example.data.CertificateType;
import com.pki.example.data.Issuer;
import com.pki.example.data.Subject;
import com.pki.example.dto.CertificateDto;
import com.pki.example.dto.CreateCertificateDto;
import com.pki.example.keystores.KeyStoreReader;
import com.pki.example.keystores.KeyStoreWriter;
import com.pki.example.models.CertificateApp;
import com.pki.example.models.UserApp;
import com.pki.example.repository.CertificateAppRepository;
import com.pki.example.util.CertificateUtils;
import org.bouncycastle.asn1.x500.X500NameBuilder;
import org.bouncycastle.asn1.x500.style.BCStyle;
import org.bouncycastle.asn1.x509.BasicConstraints;
import org.bouncycastle.asn1.x509.Extension;
import org.bouncycastle.asn1.x509.KeyUsage;
import org.bouncycastle.cert.X509CertificateHolder;
import org.bouncycastle.operator.ContentSigner;
import org.bouncycastle.operator.OperatorCreationException;
import org.bouncycastle.operator.jcajce.JcaContentSignerBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigInteger;
import java.security.*;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
public class CertificateService {
    @Autowired
    private CertificateAppRepository certificateAppRepository;
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

    public List<CertificateDto> getAllRootCertificates(){
        List<X509Certificate> certificates = keyStoreReader.getAllRoot();
        List<CertificateDto> certificateDtos = new ArrayList<>();
        for (X509Certificate cert: certificates) {
            certificateDtos.add(certificateUtils.X509CertificateToCertificateDto(cert));
        }
        return certificateDtos;
    }

    public List<CertificateDto> getAllCaCertificates(){
        List<X509Certificate> certificates = keyStoreReader.getAllCa();
        List<CertificateDto> certificateDtos = new ArrayList<>();
        for (X509Certificate cert: certificates) {
            certificateDtos.add(certificateUtils.X509CertificateToCertificateDto(cert));
        }
        return certificateDtos;
    }

    public List<CertificateDto> getAllEeCertificates(){
        List<X509Certificate> certificates = keyStoreReader.getAllEe();
        List<CertificateDto> certificateDtos = new ArrayList<>();
        for (X509Certificate cert: certificates) {
            certificateDtos.add(certificateUtils.X509CertificateToCertificateDto(cert));
        }
        return certificateDtos;
    }

    public List<CertificateDto> getMyCertificates(UserApp userApp){
        List<CertificateApp> certificateAppList = certificateAppRepository.findAllByUserAppId(userApp.getId());
        List<CertificateDto> certificateDtoList = new ArrayList<>();
        for (CertificateApp cert: certificateAppList) {
            try{
                if(cert.getCertificateType() == CertificateType.ROOT){
                    certificateDtoList.add(certificateUtils.X509CertificateToCertificateDto((X509Certificate) keyStoreReader.readCertificate("src/main/resources/static/root.jks", "password", cert.getSerialNumber().toString())));
                }else if(cert.getCertificateType() == CertificateType.CA){
                    certificateDtoList.add(certificateUtils.X509CertificateToCertificateDto((X509Certificate) keyStoreReader.readCertificate("src/main/resources/static/ca.jks", "password", cert.getSerialNumber().toString())));
                }else if(cert.getCertificateType() == CertificateType.END_ENTITY){
                    certificateDtoList.add(certificateUtils.X509CertificateToCertificateDto((X509Certificate) keyStoreReader.readCertificate("src/main/resources/static/ee.jks", "password", cert.getSerialNumber().toString())));
                }
            } catch (Exception ex){
                return null;
            }
        }
        return certificateDtoList;
    }

    public CertificateDto createCertificate(CreateCertificateDto createCertificateDto, UserApp user) {
        try{
            if (createCertificateDto.getAuthoritySubject().equals("root")) {
                KeyPair keyPair = generateKeyPair();
                Random rand = new Random();
                BigInteger newSubjectSerialNumberInteger = new BigInteger(32, rand);
                String newSubjectSerialNumber = newSubjectSerialNumberInteger.toString();
                Subject subject = generateSubject(createCertificateDto, newSubjectSerialNumber, keyPair.getPublic());
                Issuer selfIssuer = generateIssuer(createCertificateDto, newSubjectSerialNumber, keyPair.getPrivate());
                X509Certificate newCertificate = CertificateGenerator.generateCertificate(subject, selfIssuer, createCertificateDto.getStartDate(), createCertificateDto.getEndDate(), newSubjectSerialNumber, true);
                keyStoreWriter.loadKeyStore("src/main/resources/static/root.jks", "password".toCharArray());
                keyStoreWriter.write(newSubjectSerialNumber, keyPair.getPrivate(), "password".toCharArray(), newCertificate);
                keyStoreWriter.saveKeyStore("src/main/resources/static/root.jks", "password".toCharArray());
                certificateAppRepository.save(createCertificateApp(user, newSubjectSerialNumberInteger, false, CertificateType.ROOT));
                return certificateUtils.X509CertificateToCertificateDto(newCertificate);
            }
            else if (createCertificateDto.getAuthoritySubject().equals("ca")){
                Issuer issuer = getIssuer(createCertificateDto);
                if (issuer == null) return null;
                Random rand = new Random();
                BigInteger newSubjectSerialNumberInteger = new BigInteger(32, rand);
                String newSubjectSerialNumber = newSubjectSerialNumberInteger.toString();
                KeyPair keyPair = generateKeyPair();
                Subject subject = generateSubject(createCertificateDto, newSubjectSerialNumber, keyPair.getPublic());
                X509Certificate newCertificate = CertificateGenerator.generateCertificate(subject, issuer, createCertificateDto.getStartDate(), createCertificateDto.getEndDate(), newSubjectSerialNumber, true);
                keyStoreWriter.loadKeyStore("src/main/resources/static/ca.jks", "password".toCharArray());
                keyStoreWriter.write(newSubjectSerialNumber, keyPair.getPrivate(), "password".toCharArray(), newCertificate);
                keyStoreWriter.saveKeyStore("src/main/resources/static/ca.jks", "password".toCharArray());
                certificateAppRepository.save(createCertificateApp(user, newSubjectSerialNumberInteger, false, CertificateType.CA));
                return certificateUtils.X509CertificateToCertificateDto(newCertificate);
            }else if (createCertificateDto.getAuthoritySubject().equals("ee")){
                Issuer issuer = getIssuer(createCertificateDto);
                if (issuer == null) return null;
                Random rand = new Random();
                BigInteger newSubjectSerialNumberInteger = new BigInteger(32, rand);
                String newSubjectSerialNumber = newSubjectSerialNumberInteger.toString();
                KeyPair keyPair = generateKeyPair();
                Subject subject = generateSubject(createCertificateDto, newSubjectSerialNumber, keyPair.getPublic());
                X509Certificate newCertificate = CertificateGenerator.generateCertificate(subject, issuer, createCertificateDto.getStartDate(), createCertificateDto.getEndDate(), newSubjectSerialNumber, false);
                keyStoreWriter.loadKeyStore("src/main/resources/static/ee.jks", "password".toCharArray());
                keyStoreWriter.write(newSubjectSerialNumber, keyPair.getPrivate(), "password".toCharArray(), newCertificate);
                keyStoreWriter.saveKeyStore("src/main/resources/static/ee.jks", "password".toCharArray());
                certificateAppRepository.save(createCertificateApp(user, newSubjectSerialNumberInteger, false, CertificateType.END_ENTITY));
                return certificateUtils.X509CertificateToCertificateDto(newCertificate);
            }
            return null;
        }catch (Exception e){
            return null;
        }

    }

    private Issuer getIssuer(CreateCertificateDto createCertificateDto) {
        Issuer issuer = null;
        try{
            issuer = keyStoreReader.readIssuerFromStore("src/main/resources/static/ca.jks", createCertificateDto.getIssuerSerialNumber(), "password".toCharArray(), "password".toCharArray());
        }catch(Exception e){
            e.printStackTrace();
        }
        try{
            if(issuer == null){
                issuer = keyStoreReader.readIssuerFromStore("src/main/resources/static/root.jks", createCertificateDto.getIssuerSerialNumber(), "password".toCharArray(), "password".toCharArray());
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return issuer;
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

    public Issuer generateIssuer(CreateCertificateDto certificateDTO, String serialNumber, PrivateKey privateKey){
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

    private CertificateApp createCertificateApp(UserApp userApp, BigInteger serialNumber, boolean revoked, CertificateType certificateType){
        CertificateApp certificateApp = new CertificateApp();
        certificateApp.setUserApp(userApp);
        certificateApp.setSerialNumber(serialNumber);
        certificateApp.setRevoked(revoked);
        certificateApp.setCertificateType(certificateType);
        return certificateApp;
    }
}
