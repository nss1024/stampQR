package com.stampqr.dbaccessService.service;

import com.stampqr.dbaccessService.repos.MobileUsersDetailsReopsitory;
import com.stampqr.dbaccessService.resources.MobileUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MobileUserDetailsServiceimpl implements MobileUserDetailsService{

    @Autowired
    MobileUsersDetailsReopsitory mobileUsersDetailsReopsitory;


    @Override
    public void addNewMobileUserDetails(String username, String fName, String lName, String email, String company,Long creatingUserId, Boolean active) {
        MobileUserDetails mud = new MobileUserDetails();
        mud.setMobileUsername(username);
        mud.setFirstName(fName);
        mud.setLastName(lName);
        mud.setEmail(email);
        mud.setCompany(company);
        mud.setCreatingUserId(creatingUserId);
        mud.setUserActive(active);

        mobileUsersDetailsReopsitory.save(mud);

    }

    @Override
    public List<MobileUserDetails> getMobileUserDetailsByUserId(Long userId) {
        return mobileUsersDetailsReopsitory.getMobileUserDetailsByCreatingUserId(userId);
    }

    @Override
    public void updateMobileUserDetails(Long id, String username, String fName, String lName, String email, String company, Boolean active) {
        MobileUserDetails mud = mobileUsersDetailsReopsitory.getReferenceById(id);
        mud.setFirstName(fName);
        mud.setLastName(lName);
        mud.setEmail(email);
        mud.setCompany(company);
        mud.setUserActive(active);

        mobileUsersDetailsReopsitory.save(mud);
    }

    @Override
    public List<MobileUserDetails> getAllMobileUsers() {
        return mobileUsersDetailsReopsitory.findAll();
    }
}
