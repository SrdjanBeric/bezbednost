package com.pki.example.certificates;

import com.pki.example.data.Issuer;
import com.pki.example.data.Subject;
import com.pki.example.data.Certificate;
import org.bouncycastle.asn1.x500.X500NameBuilder;
import org.bouncycastle.asn1.x500.style.BCStyle;
import org.springframework.stereotype.Component;

import java.security.*;
import java.security.cert.X509Certificate;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class CertificateExample {

    public Subject generateSubject(String serialNumber) {
        KeyPair keyPairSubject = generateKeyPair();

        //klasa X500NameBuilder pravi X500Name objekat koji predstavlja podatke o vlasniku
        X500NameBuilder builder = new X500NameBuilder(BCStyle.INSTANCE);
        builder.addRDN(BCStyle.CN, "Admin");
        builder.addRDN(BCStyle.SURNAME, "Admin");
        builder.addRDN(BCStyle.GIVENNAME, "Admin");
        builder.addRDN(BCStyle.O, "UNS-FTN");
        builder.addRDN(BCStyle.OU, "Admin");
        builder.addRDN(BCStyle.C, "RS");
        builder.addRDN(BCStyle.E, "Admin@uns.ac.rs");
        //UID (USER ID) je ID korisnika
        builder.addRDN(BCStyle.UID, "123456");

        return new Subject(keyPairSubject.getPublic(), builder.build(), serialNumber);
    }

    public Issuer generateIssuer(String serialNumber) {
        KeyPair kp = generateKeyPair();
        X500NameBuilder builder = new X500NameBuilder(BCStyle.INSTANCE);
        builder.addRDN(BCStyle.CN, "IT sluzba");
        builder.addRDN(BCStyle.SURNAME, "sluzba");
        builder.addRDN(BCStyle.GIVENNAME, "IT");
        builder.addRDN(BCStyle.O, "UNS-FTN");
        builder.addRDN(BCStyle.OU, "Katedra za informatiku");
        builder.addRDN(BCStyle.C, "RS");
        builder.addRDN(BCStyle.E, "itsluzba@uns.ac.rs");
        //UID (USER ID) je ID korisnika
        builder.addRDN(BCStyle.UID, "654321");

        //Kreiraju se podaci za issuer-a, sto u ovom slucaju ukljucuje:
        // - privatni kljuc koji ce se koristiti da potpise sertifikat koji se izdaje
        // - podatke o vlasniku sertifikata koji izdaje nov sertifikat
        return new Issuer(kp.getPrivate(), serialNumber, builder.build());
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

    public com.pki.example.data.Certificate getCertificate(String serialNumberIssuer, String serialNumberSubject) {

        try {
            Issuer issuer = generateIssuer(serialNumberIssuer);
            Subject subject = generateSubject(serialNumberSubject);

            //Datumi od kad do kad vazi sertifikat
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date startDate = sdf.parse("2023-03-25");
            Date endDate = sdf.parse("2028-03-25");

            X509Certificate certificate = CertificateGenerator.generateCertificate(subject,
                    issuer, startDate, endDate, "1", true);

            return new com.pki.example.data.Certificate(subject, issuer,
                    "1", startDate, endDate, certificate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return null;
    }


}
