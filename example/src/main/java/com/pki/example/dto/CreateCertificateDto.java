package com.pki.example.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.util.Date;

@NoArgsConstructor
@Getter
@Setter
public class CreateCertificateDto {
    @NotNull(message = "CommonName is mandatory")
    private String commonNameSubject;
    @NotNull(message = "Name is mandatory")
    private String nameSubject;
    @NotNull(message = "Name is mandatory")
    private String surnameSubject;
    @NotNull(message = "Name is mandatory")
    private String usernameSubject;
    @NotNull(message = "Name is mandatory")
    private String countrySubject;
    @JsonFormat(pattern="yyyy-MM-dd")
    private Date startDate;
    @JsonFormat(pattern="yyyy-MM-dd")
    private Date endDate;
    // authoritySubject moze biti: root, ca, ee
    @NotNull(message = "Authority is mandatory")
    private String authoritySubject;
    @NotNull(message = "Issuer serial number is mandatory")
    private String issuerSerialNumber;

    // dodeljujemo username korisnika kome zelimo da izdamo sertifikat
    @NotNull(message = "Owner Username is mandatory")
    private String ownerUsername;
}
