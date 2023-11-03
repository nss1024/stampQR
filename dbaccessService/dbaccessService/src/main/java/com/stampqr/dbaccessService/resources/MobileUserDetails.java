package com.stampqr.dbaccessService.resources;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name="mobile_user_details")
public class MobileUserDetails {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="mobile_userid")
    private Long mobileUserId;
    @Column(name="mobile_username")
    private String mobileUsername;
    @Column(name="firstName")
    private String firstName;
    @Column(name="lastName")
    private String lastName;
    @Column(name="email")
    private String email;
    @Column(name="company")
    private String company;
    @Column(name="creatingUser")
    private Long creatingUserId;
    @Column(name="mobileUserActive")
    private Boolean userActive;

}
