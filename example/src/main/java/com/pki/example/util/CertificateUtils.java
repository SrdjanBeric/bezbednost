package com.pki.example.util;

import com.pki.example.dto.CertificateDto;
import org.apache.logging.log4j.message.StringMapMessage;
import org.bouncycastle.asn1.x500.RDN;
import org.bouncycastle.asn1.x500.X500Name;
import org.bouncycastle.asn1.x500.style.BCStyle;
import org.bouncycastle.asn1.x500.style.IETFUtils;
import org.bouncycastle.cert.jcajce.JcaX509CertificateHolder;
import org.springframework.stereotype.Component;

import java.math.BigInteger;
import java.security.*;
import java.security.cert.CertificateEncodingException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;

@Component
public class CertificateUtils {

    public CertificateDto X509CertificateToCertificateDto(X509Certificate certificate){
        CertificateDto certDto = new CertificateDto();
        JcaX509CertificateHolder certHolder = null;
        try {
            certHolder = new JcaX509CertificateHolder(certificate);
        } catch (CertificateEncodingException e) {
            StringMapMessage message = new StringMapMessage();
            message.put("msg", "Certificate encoding error: " + e.getMessage());
            message.put("issuer", certificate.getIssuerX500Principal().getName());
            message.put("subject", certificate.getSubjectX500Principal().getName());
            message.put("serial", certificate.getSerialNumber().toString());
            throw new RuntimeException(e);
        }
        X500Name subject = certHolder.getSubject();
        X500Name issuer = certHolder.getIssuer();
        String authority = "ca";
        try {
            if(isSelfSigned(certificate)) authority = "root";
            else if(certificate.getBasicConstraints() == -1) authority = "endEntity";
        } catch (CertificateException | InvalidKeyException | NoSuchAlgorithmException | NoSuchProviderException e) {
            StringMapMessage message = new StringMapMessage();
            message.put("msg", "Certificate exception: " + e.getMessage());
            message.put("issuer", certificate.getIssuerX500Principal().getName());
            message.put("subject", certificate.getSubjectX500Principal().getName());
            message.put("serial", certificate.getSerialNumber().toString());
            throw new RuntimeException(e);
        }
        String temp;
        RDN cn;
        BigInteger subjectSerialNumber = certHolder.getSerialNumber();
//        BigInteger issuerSerialNumber = (BigInteger) certHolder.getIssuer().getRDNs(BCStyle.SERIALNUMBER);
        certDto.setSerialNumberSubject(subjectSerialNumber.toString());
//        certDto.setSerialNumberIssuer(issuerSerialNumber.toString());
        if(subject.getRDNs(BCStyle.CN).length > 0) {
            cn = subject.getRDNs(BCStyle.CN)[0];
            temp = IETFUtils.valueToString(cn.getFirst().getValue());
            certDto.setCommonNameSubject(temp);
        }
        if(subject.getRDNs(BCStyle.NAME).length > 0) {
            cn = subject.getRDNs(BCStyle.NAME)[0];
            temp = IETFUtils.valueToString(cn.getFirst().getValue());
            certDto.setNameSubject(temp);
        }
        if(subject.getRDNs(BCStyle.SURNAME).length > 0) {
            cn = subject.getRDNs(BCStyle.SURNAME)[0];
            temp = IETFUtils.valueToString(cn.getFirst().getValue());
            certDto.setSurnameSubject(temp);
        }
        if(subject.getRDNs(BCStyle.UID).length > 0) {
            cn = subject.getRDNs(BCStyle.UID)[0];
            temp = IETFUtils.valueToString(cn.getFirst().getValue());
            certDto.setUsernameSubject(temp);
        }
        if(subject.getRDNs(BCStyle.C).length > 0) {
            cn = subject.getRDNs(BCStyle.C)[0];
            temp = IETFUtils.valueToString(cn.getFirst().getValue());
            certDto.setCountrySubject(temp);
        }
        if(subject.getRDNs(BCStyle.SERIALNUMBER).length > 0) {
            cn = subject.getRDNs(BCStyle.SERIALNUMBER)[0];
            temp = IETFUtils.valueToString(cn.getFirst().getValue());
            certDto.setSerialNumberSubject(temp);
        }

        //--------------------------------------------------------------------------

        if(issuer.getRDNs(BCStyle.CN).length > 0) {
            cn = issuer.getRDNs(BCStyle.CN)[0];
            temp = IETFUtils.valueToString(cn.getFirst().getValue());
            certDto.setCommonNameIssuer(temp);
        }
        if(issuer.getRDNs(BCStyle.NAME).length > 0) {
            cn = issuer.getRDNs(BCStyle.NAME)[0];
            temp = IETFUtils.valueToString(cn.getFirst().getValue());
            certDto.setNameIssuer(temp);
        }
        if(issuer.getRDNs(BCStyle.SURNAME).length > 0) {
            cn = issuer.getRDNs(BCStyle.SURNAME)[0];
            temp = IETFUtils.valueToString(cn.getFirst().getValue());
            certDto.setSurnameIssuer(temp);
        }
        if(issuer.getRDNs(BCStyle.UID).length > 0) {
            cn = issuer.getRDNs(BCStyle.UID)[0];
            temp = IETFUtils.valueToString(cn.getFirst().getValue());
            certDto.setUsernameIssuer(temp);
        }
        if(issuer.getRDNs(BCStyle.C).length > 0) {
            cn = issuer.getRDNs(BCStyle.C)[0];
            temp = IETFUtils.valueToString(cn.getFirst().getValue());
            certDto.setCountryIssuer(temp);
        }
        if(issuer.getRDNs(BCStyle.SERIALNUMBER).length > 0) {
            cn = issuer.getRDNs(BCStyle.SERIALNUMBER)[0];
            temp = IETFUtils.valueToString(cn.getFirst().getValue());
            certDto.setSerialNumberIssuer(temp);
        }
        certDto.setStartDate(certificate.getNotBefore());
        certDto.setEndDate(certificate.getNotAfter());
        certDto.setAuthoritySubject(authority);
        List<Integer> keyUsages = new ArrayList<>();
        if(certificate.getKeyUsage() != null){
            for(int i = 8; i >= 0; i-- ){
                if(i == 8 && certificate.getKeyUsage()[i]) {
                    keyUsages.add(32768);
                    continue;
                }
                if(certificate.getKeyUsage()[i])
                    keyUsages.add((int)Math.pow(2, 7 - i));
            }
        }
        certDto.setKeyUsages(keyUsages);
        return certDto;
    }

    public boolean isSelfSigned(X509Certificate cert)
            throws CertificateException, InvalidKeyException,
            NoSuchAlgorithmException, NoSuchProviderException {

        return verifySignatures(cert, cert.getPublicKey());
    }

    //    This code verifies if a digital signature on a certificate is valid by checking if
    //    the provided public key matches the signature algorithm used to sign the certificate.
    private boolean verifySignatures(X509Certificate cert, PublicKey key) {
        String sigAlg = cert.getSigAlgName();
        String keyAlg = key.getAlgorithm();
        sigAlg = sigAlg != null ? sigAlg.trim().toUpperCase() : "";
        keyAlg = keyAlg != null ? keyAlg.trim().toUpperCase() : "";
        if (keyAlg.length() >= 2 && sigAlg.endsWith(keyAlg)) {
            try {
                cert.verify(key);
                return true;
            } catch (SignatureException | CertificateException | NoSuchAlgorithmException | InvalidKeyException |
                    NoSuchProviderException e) {
                return false;
            }
        } else {
            return false;
        }
    }
}
