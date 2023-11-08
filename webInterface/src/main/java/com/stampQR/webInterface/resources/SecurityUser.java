package com.stampQR.webInterface.resources;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class SecurityUser implements UserDetails {

    private Users users;
    private List<Authorities> authoritiesList;

    public SecurityUser (Users u, List<Authorities> al){
        this.users = u;
        this.authoritiesList = al;

    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority>authorities=new ArrayList<>();
        for (Authorities a: authoritiesList) {
            authorities.add(new SimpleGrantedAuthority(a.getAuthority()));
            System.out.println(a.getAuthority());
        }
        System.out.println(users.getUsername()+" "+users.getPassword());
        return authorities;
    }

    @Override
    public String getPassword() {
        return users.getPassword();
    }

    @Override
    public String getUsername() {
        return users.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return users.getEnabled();
    }
}
