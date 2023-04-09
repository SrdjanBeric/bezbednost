package com.pki.example.repository;

import com.pki.example.models.CertificateApp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CertificateAppRepository extends JpaRepository<CertificateApp, Long> {

    @Query("select c from CertificateApp c where c.userApp.id=:userAppId")
    public List<CertificateApp> findAllByUserAppId(@Param("userAppId") Long userAppId);
}
