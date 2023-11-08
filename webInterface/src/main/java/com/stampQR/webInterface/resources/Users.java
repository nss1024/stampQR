package com.stampQR.webInterface.resources;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter

public class Users {


    private String username;
    private String password;
    private Boolean enabled;

}
