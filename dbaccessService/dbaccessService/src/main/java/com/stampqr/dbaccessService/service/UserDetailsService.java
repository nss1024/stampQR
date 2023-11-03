package com.stampqr.dbaccessService.service;

import com.stampqr.dbaccessService.resources.UserDetails;
import com.stampqr.dbaccessService.wrapperClasses.UserDetailsList;

import java.util.List;

public interface UserDetailsService {

    public void saveNewUserDetails(String userName, String fName, String lName, String email, String company);

    public void updateUserDetails(Long id, String fName, String lName, String email, String company);

    public UserDetails getUserDetails(Long id);

    public UserDetails getUserDetails(String userName);

    public UserDetailsList getAllUserDetails ();

}
