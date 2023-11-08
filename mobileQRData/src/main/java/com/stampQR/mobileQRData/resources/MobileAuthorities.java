package com.stampQR.mobileQRData.resources;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "mobile_authorities")
public class MobileAuthorities {

    @Id
    @Column(name = "username")
    private String username;
    @Column(name = "authority")
    private String authority;


}
