package com.stampqr.dbaccessService.service;

import com.stampqr.dbaccessService.resources.Authorities;
import com.stampqr.dbaccessService.wrapperClasses.AuthoritiesList;

import java.util.List;

public interface AuthoritiesService {

    public void setAuthority(String authority, String userName);

    public void updateAuthority(String authority, String userName);

    public List<Authorities> getAuthoritiesByUsername(String username);

    public AuthoritiesList getAllAuthorities();

    public Boolean isAdmin(String username, String authority);

}
