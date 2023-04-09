package com.pki.example.repository;

import com.pki.example.models.CertificateApp;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CertificateAppRepository extends JpaRepository<CertificateApp, Long> {

}
