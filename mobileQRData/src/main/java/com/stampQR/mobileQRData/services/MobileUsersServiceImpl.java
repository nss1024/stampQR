package com.stampQR.mobileQRData.services;


import com.stampQR.mobileQRData.repos.MobileUsersRepository;
import com.stampQR.mobileQRData.resources.MobileUsers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MobileUsersServiceImpl implements MobileUsersService{

    @Autowired
    private MobileUsersRepository mobileUsersRepository;


    @Override
    public MobileUsers getMobileUserByUsername(String username) {
        MobileUsers mu = mobileUsersRepository.getMobileUsersByUsername(username);
        return mu;
    }


}
