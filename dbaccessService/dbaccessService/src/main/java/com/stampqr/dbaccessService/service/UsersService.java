package com.stampqr.dbaccessService.service;

import com.stampqr.dbaccessService.resources.Users;
import com.stampqr.dbaccessService.wrapperClasses.UsersList;

public interface UsersService {

    public boolean saveNewUser(String userName, String password);

    public boolean doesUserExist(String userName);

    public void updateUserStatus(String userName, Boolean active);

    public void updateUserPassword(String userName, String password);

    public UsersList getAllUsers();

    public Boolean isEnabled(String userName);

    public Users getUserByUsername(String username);

}
