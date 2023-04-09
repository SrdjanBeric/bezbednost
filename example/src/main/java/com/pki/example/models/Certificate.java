package com.pki.example.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Certificate {
    @Id
    private Long serialNumber;
    @Column
    private boolean revoked;

    @ManyToOne(fetch = FetchType.EAGER)
    private UserApp userApp;
}
