package com.stampqr.dbaccessService.service;

import com.stampqr.dbaccessService.resources.MobileUserDetails;

import java.util.List;

public interface MobileUserDetailsService {

    public void addNewMobileUserDetails(String username, String fName, String lName, String email, String company, Long creatingUserId, Boolean active);

    public List<MobileUserDetails> getMobileUserDetailsByUserId(Long userId);

    public void updateMobileUserDetails(Long id, String username, String fName, String lName, String email, String company, Boolean active);

    public List<MobileUserDetails> getAllMobileUsers();

}
