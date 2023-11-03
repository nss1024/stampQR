package com.stampQR.webInterface.resources;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserDetails {


    private Long userId;
    private String username;
    private String firstName;
    private String lastName;
    private String email;
    private String company;



}
