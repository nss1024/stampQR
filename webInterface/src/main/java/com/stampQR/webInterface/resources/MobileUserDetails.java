package com.stampQR.webInterface.resources;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter

public class MobileUserDetails {


    private Long mobileUserId;
    private String mobileUsername;
    private String firstName;
    private String lastName;
    private String email;
    private String company;
    private Long creatingUserId;
    private Boolean userActive;

}
