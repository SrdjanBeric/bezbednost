package com.pki.example;

import com.pki.example.certificates.CertificateExample;
import com.pki.example.data.Issuer;
import com.pki.example.dto.CertificateDto;
import com.pki.example.keystores.KeyStoreReader;
import com.pki.example.keystores.KeyStoreWriter;
import com.pki.example.service.CertificateService;
import com.pki.example.util.CertificateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationContextFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import java.security.*;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.List;

@SpringBootApplication
public class ExampleApplication {

	private static CertificateUtils certificateUtils;

	private static CertificateExample certExample;

	private static KeyStoreReader keyStoreReader;

	private static KeyStoreWriter keyStoreWriter;

	private static ApplicationContext context;

	private static CertificateService certificateService;

	public static void main(String[] args) throws CertificateException {
		context = SpringApplication.run(ExampleApplication.class, args);

//		certificateService = (CertificateService) context.getBean("certificateService");
//		certificateUtils = (CertificateUtils) context.getBean("certificateUtils");
//		keyStoreReader = (KeyStoreReader) context.getBean("keyStoreReader");
//		keyStoreWriter = (KeyStoreWriter) context.getBean("keyStoreWriter");
//		certExample = (CertificateExample) context.getBean("certificateExample");
//
//		com.pki.example.data.Certificate certificate = certExample.getCertificate();
//		System.out.println("Novi sertifikat:");
//		System.out.println(certificate.getX509Certificate());
//
//		// Inicijalizacija fajla za cuvanje sertifikata
//		System.out.println("Cuvanje certifikata u jks fajl:");
//		keyStoreWriter.loadKeyStore("src/main/resources/static/example.jks",  "password".toCharArray());
//		PrivateKey pk = certificate.getIssuer().getPrivateKey();
//		keyStoreWriter.write("1", pk, "password".toCharArray(), certificate.getX509Certificate());
//		keyStoreWriter.saveKeyStore("src/main/resources/static/example.jks",  "password".toCharArray());
//		System.out.println("Cuvanje certifikata u jks fajl zavrseno.");
//
//		System.out.println("Ucitavanje sertifikata iz jks fajla:");
//		Certificate loadedCertificate = keyStoreReader.readCertificate("src/main/resources/static/example.jks", "password", "1");
//		System.out.println(loadedCertificate);
//		X509Certificate certtt = (X509Certificate) keyStoreReader.readCertificate("src/main/resources/static/example.jks", "password", "3146402376");
//
//		Issuer issuer1 = keyStoreReader.readIssuerFromStore("src/main/resources/static/example.jks", "1569848879", "password".toCharArray(), "password".toCharArray());
//		Issuer issuer2 = keyStoreReader.readIssuerFromStore("src/main/resources/static/example.jks", "2403031204", "password".toCharArray(), "password".toCharArray());
//		Issuer issuer3 = keyStoreReader.readIssuerFromStore("src/main/resources/static/example.jks", "1", "password".toCharArray(), "password".toCharArray());
//
//		System.out.println("Provera potpisa:");
//		// to do
	}

}
