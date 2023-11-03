package com.stampqr.dbaccessService.service;

import com.stampqr.dbaccessService.repos.MobileAuthoritiesRepository;
import com.stampqr.dbaccessService.resources.MobileAuthorities;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MobileAuthoritiesServiceImpl implements MobileAuthoritiesService{

    @Autowired
    private MobileAuthoritiesRepository mobileAuthoritiesRepository;

    @Override
    public void SetMobileAuthority(String username) {
        MobileAuthorities ma = new MobileAuthorities();
        ma.setUsername(username);
        ma.setAuthority("MOBILE");
        mobileAuthoritiesRepository.save(ma);
    }

    @Override
    public void deleteAuthority(String username) {
        mobileAuthoritiesRepository.deleteByUsername(username);
    }
}
