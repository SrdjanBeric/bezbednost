package com.pki.example.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserApp implements UserDetails {
    @Id
    @SequenceGenerator(name = "userAppSeqGen", sequenceName = "userAppSeq", initialValue = 1, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "userAppSeqGen")
    @Column(name="id", unique=true, nullable=false)
    private long id;

    @Column(unique = true, nullable = false)
    @NotBlank
    private String username;

    @JsonIgnore
    @Column
    @NotBlank
    private String password;

    @ManyToOne(fetch = FetchType.EAGER)
    private Role role;

    @OneToMany
    (mappedBy = "userApp", fetch = FetchType.EAGER, cascade= CascadeType.ALL, orphanRemoval=true)
    private List<CertificateApp> certificateList;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Arrays.asList(new SimpleGrantedAuthority(this.role.getName()));
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
