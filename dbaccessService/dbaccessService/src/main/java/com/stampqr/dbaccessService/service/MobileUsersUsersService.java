package com.stampqr.dbaccessService.service;

import java.util.List;

public interface MobileUsersUsersService {

    public List<String> getMobileUsersOfUser (String userName);

    public List<String> getUsersOfMobileUser(String mobileUser);

}
