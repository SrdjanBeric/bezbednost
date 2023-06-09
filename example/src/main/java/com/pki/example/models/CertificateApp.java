package com.pki.example.models;

import com.pki.example.data.CertificateType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigInteger;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CertificateApp {
    @Id
    private BigInteger serialNumber;
    @Column
    private boolean revoked;
    @Column
    @Enumerated(EnumType.STRING)
    private CertificateType certificateType;
    @ManyToOne(fetch = FetchType.EAGER)
    private UserApp userApp;
}
