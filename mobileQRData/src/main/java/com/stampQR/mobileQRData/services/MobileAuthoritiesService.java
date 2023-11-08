package com.stampQR.mobileQRData.services;

import com.stampQR.mobileQRData.resources.MobileAuthorities;

import java.util.List;

public interface MobileAuthoritiesService {

    public void deleteAuthority(String username);

    public List<MobileAuthorities> getAuthoritiesByUserame(String username);

}
