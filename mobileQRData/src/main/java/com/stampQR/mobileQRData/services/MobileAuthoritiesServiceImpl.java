package com.stampQR.mobileQRData.services;


import com.stampQR.mobileQRData.repos.MobileAuthoritiesRepository;
import com.stampQR.mobileQRData.resources.MobileAuthorities;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MobileAuthoritiesServiceImpl implements MobileAuthoritiesService{

    @Autowired
    private MobileAuthoritiesRepository mobileAuthoritiesRepository;

    @Override
    public void deleteAuthority(String username) {
        mobileAuthoritiesRepository.deleteByUsername(username);
    }

    @Override
    public List<MobileAuthorities> getAuthoritiesByUserame(String username) {
        List<MobileAuthorities> mobileUserAuthorities = mobileAuthoritiesRepository.findByUsername(username);
        return mobileUserAuthorities;
    }
}
