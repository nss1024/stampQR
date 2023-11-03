package com.stampqr.dbaccessService.service;

import com.stampqr.dbaccessService.repos.MobileUsersUsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MobileUserUserServiceImpl implements MobileUsersUsersService{

    @Autowired
    MobileUsersUsersRepository mobileUsersUsersRepository;
    @Override
    public List<String> getMobileUsersOfUser(String userName) {
        return mobileUsersUsersRepository.findByUserName(userName);
    }

    @Override
    public List<String> getUsersOfMobileUser(String mobileUser) {
        return mobileUsersUsersRepository.findByMobileUserName(mobileUser);
    }
}
