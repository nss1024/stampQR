package com.stampqr.dbaccessService.service;

import com.stampqr.dbaccessService.resources.MobileUsers;

public interface MobileUsersService {

    public void addNewMobileUser(String username, String password);

    public MobileUsers getMobileUserByUsername( String username );

}
