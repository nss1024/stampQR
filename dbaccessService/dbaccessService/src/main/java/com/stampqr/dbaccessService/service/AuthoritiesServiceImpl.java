package com.stampqr.dbaccessService.service;

import com.stampqr.dbaccessService.repos.AuthoritiesRepository;
import com.stampqr.dbaccessService.resources.Authorities;
import com.stampqr.dbaccessService.wrapperClasses.AuthoritiesList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthoritiesServiceImpl implements AuthoritiesService{

    @Autowired
    private AuthoritiesRepository authoritiesRepository;

    public Authorities getAuthoritiesByUsername(String userName){
        Authorities a = authoritiesRepository.getAuthoritiesByUsername(userName);
        return a;
    }

    @Override
    public void setAuthority(String authority, String userName) {
        Authorities a = new Authorities();
        a.setAuthority(authority);
        a.setUsername(userName);
        authoritiesRepository.save(a);

    }

    @Override
    public void updateAuthority(String authority, String userName) {
        Authorities a = authoritiesRepository.getAuthoritiesByUsername(userName);
        a.setAuthority(authority);
    }

    @Override
    public AuthoritiesList getAllAuthorities() {
        AuthoritiesList al = new AuthoritiesList();
        al.setAuthoritiesList(authoritiesRepository.findAll());
        return al;
    }

    @Override
    public Boolean isAdmin(String username, String authority) {
        Authorities a = authoritiesRepository.findByUsernameAndAuthority(username,authority);

        if(a==null){return Boolean.FALSE;}
        else{return Boolean.TRUE;}

    }
}
