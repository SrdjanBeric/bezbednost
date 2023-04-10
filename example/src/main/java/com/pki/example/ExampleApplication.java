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

		certificateService = (CertificateService) context.getBean("certificateService");
		certificateUtils = (CertificateUtils) context.getBean("certificateUtils");
		keyStoreReader = (KeyStoreReader) context.getBean("keyStoreReader");
		keyStoreWriter = (KeyStoreWriter) context.getBean("keyStoreWriter");
		certExample = (CertificateExample) context.getBean("certificateExample");


		// odkomentarisi ukoliko ne postoje root.jks, ca.jks, ee.jks
//		createRootJks();
//		createCaJks();
//		createEeJks();
		//
	}

	private static void createRootJks(){
		com.pki.example.data.Certificate certificate = certExample.getCertificate("1", "1");
		System.out.println("Novi sertifikat:");
		System.out.println(certificate.getX509Certificate());

		// Inicijalizacija fajla za cuvanje sertifikata
		System.out.println("Cuvanje certifikata u jks fajl:");
		keyStoreWriter.loadKeyStore(null,  "password".toCharArray());
		PrivateKey pk = certificate.getIssuer().getPrivateKey();
		keyStoreWriter.write("1", pk, "password".toCharArray(), certificate.getX509Certificate());
		keyStoreWriter.saveKeyStore("src/main/resources/static/root.jks",  "password".toCharArray());
	}

	private static void createCaJks(){
		com.pki.example.data.Certificate certificate = certExample.getCertificate("1", "2");
		System.out.println("Novi sertifikat:");
		System.out.println(certificate.getX509Certificate());

		// Inicijalizacija fajla za cuvanje sertifikata
		System.out.println("Cuvanje certifikata u jks fajl:");
		keyStoreWriter.loadKeyStore(null,  "password".toCharArray());
		PrivateKey pk = certificate.getIssuer().getPrivateKey();
		keyStoreWriter.write("2", pk, "password".toCharArray(), certificate.getX509Certificate());
		keyStoreWriter.saveKeyStore("src/main/resources/static/ca.jks",  "password".toCharArray());
	}

	private static void createEeJks(){
		com.pki.example.data.Certificate certificate = certExample.getCertificate("1", "3");
		System.out.println("Novi sertifikat:");
		System.out.println(certificate.getX509Certificate());

		// Inicijalizacija fajla za cuvanje sertifikata
		System.out.println("Cuvanje certifikata u jks fajl:");
		keyStoreWriter.loadKeyStore(null,  "password".toCharArray());
		PrivateKey pk = certificate.getIssuer().getPrivateKey();
		keyStoreWriter.write("3", pk, "password".toCharArray(), certificate.getX509Certificate());
		keyStoreWriter.saveKeyStore("src/main/resources/static/ee.jks",  "password".toCharArray());
	}

}
