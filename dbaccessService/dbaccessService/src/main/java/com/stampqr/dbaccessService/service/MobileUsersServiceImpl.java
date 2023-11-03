package com.stampqr.dbaccessService.service;


import com.stampqr.dbaccessService.repos.MobileUsersRepository;
import com.stampqr.dbaccessService.resources.MobileUsers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MobileUsersServiceImpl implements MobileUsersService{

    @Autowired
    private MobileUsersRepository mobileUsersRepository;


    @Override
    public void addNewMobileUser(String username, String password) {
        MobileUsers mu = new MobileUsers(username,password,Boolean.TRUE);
        mobileUsersRepository.save(mu);
    }

    @Override
    public MobileUsers getMobileUserByUsername(String username) {
        MobileUsers mu = mobileUsersRepository.getMobileUsersByUsername(username);
        return mu;
    }


}
