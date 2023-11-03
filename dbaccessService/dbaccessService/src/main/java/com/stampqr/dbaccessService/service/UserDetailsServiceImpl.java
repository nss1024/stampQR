package com.stampqr.dbaccessService.service;

import com.stampqr.dbaccessService.repos.UserDetailsRepository;
import com.stampqr.dbaccessService.resources.UserDetails;
import com.stampqr.dbaccessService.wrapperClasses.UserDetailsList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserDetailsServiceImpl implements UserDetailsService{

    @Autowired
    private UserDetailsRepository userDetailsRepository;

    public void saveNewUserDetails(String userName, String fName, String lName, String email, String company){
        UserDetails ud = new UserDetails();
        ud.setUsername(userName);
        ud.setFirstName(fName);
        ud.setLastName(lName);
        ud.setEmail(email);
        ud.setCompany(company);
        userDetailsRepository.save(ud);
    }

    public void updateUserDetails(Long id, String fName, String lName, String email, String company){
        UserDetails existingUserDetails = userDetailsRepository.getReferenceById(id);
        existingUserDetails.setFirstName(fName);
        existingUserDetails.setLastName(lName);
        existingUserDetails.setEmail(email);
        existingUserDetails.setCompany(company);
        userDetailsRepository.save(existingUserDetails);
    }

    public UserDetails getUserDetails(Long id){
        return  userDetailsRepository.getReferenceById(id);
    }

    public UserDetails getUserDetails(String userName){
        return userDetailsRepository.getUserDetailsByUserName(userName);
    }

    public UserDetailsList getAllUserDetails (){
        UserDetailsList udl = new UserDetailsList();
        udl.setUserDetailsList(userDetailsRepository.findAll());

        return udl;
    }

}
