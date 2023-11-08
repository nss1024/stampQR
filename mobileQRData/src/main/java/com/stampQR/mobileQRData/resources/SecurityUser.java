package com.stampQR.mobileQRData.resources;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class SecurityUser implements UserDetails {

    private MobileUsers mu;
    private List <MobileAuthorities> mal;
    public SecurityUser(MobileUsers mu, List<MobileAuthorities> mal){
        this.mu = mu;
        this.mal = mal;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority>authorities=new ArrayList<>();
        for (MobileAuthorities ma: mal) {
            authorities.add(new SimpleGrantedAuthority(ma.getAuthority()));
            System.out.println(ma.getAuthority());
        }
        System.out.println(mu.getUsername()+" "+mu.getPassword());
        return authorities;
    }

    @Override
    public String getPassword() {
        return mu.getPassword();
    }

    @Override
    public String getUsername() {
        return mu.getUsername();
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
        return mu.getEnabled();
    }
}
