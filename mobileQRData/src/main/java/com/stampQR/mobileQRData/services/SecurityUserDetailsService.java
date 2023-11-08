package com.stampQR.mobileQRData.services;

import com.stampQR.mobileQRData.resources.MobileAuthorities;
import com.stampQR.mobileQRData.resources.MobileUsers;
import com.stampQR.mobileQRData.resources.SecurityUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class SecurityUserDetailsService implements UserDetailsService {

    @Autowired
    MobileUsersServiceImpl mobileUsersService;

    @Autowired
    MobileAuthoritiesServiceImpl mobileAuthoritiesService;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        MobileUsers mu = mobileUsersService.getMobileUserByUsername(username);
        List<MobileAuthorities> mal = mobileAuthoritiesService.getAuthoritiesByUserame(username);
        SecurityUser su = new SecurityUser(mu,mal);

        if(Objects.nonNull(mu)|Objects.nonNull(mal)){
            return su;
        }else{
            throw new UsernameNotFoundException("Username not found : "+username);
        }


    }
}
