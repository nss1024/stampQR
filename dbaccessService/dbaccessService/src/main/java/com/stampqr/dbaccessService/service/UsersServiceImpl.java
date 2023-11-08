package com.stampqr.dbaccessService.service;

import com.stampqr.dbaccessService.repos.UsersRepository;
import com.stampqr.dbaccessService.resources.Users;
import com.stampqr.dbaccessService.wrapperClasses.UsersList;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UsersServiceImpl implements UsersService{

    @Autowired
    private UsersRepository usersRepository;

    @Override
    //Returns true if new user was saved , false otherwise
    public boolean saveNewUser(String userName, String password) {
        Users newUser = null;
        Users u = new Users(userName,password,Boolean.TRUE);
        //The save method returns an instance of the entity it saved
        newUser = usersRepository.save(u);

        return !newUser.equals(null);

    }

    @Override
    public boolean doesUserExist(String userName) {

        Users u =  usersRepository.getReferenceById(userName);
        boolean userexists = true;

        if(u.equals(null)) userexists = false;

        return userexists;
    }

    public void updateUserStatus(String userName, Boolean active){
        Users u = usersRepository.getReferenceById(userName);
        u.setEnabled(active);
        usersRepository.save(u);
    }

    public void updateUserPassword(String userName, String password){
        Users u = usersRepository.getReferenceById(userName);
        u.setPassword(password);
        usersRepository.save(u);

    }

    @Override
    public UsersList getAllUsers() {
        UsersList ul = new UsersList();
        ul.setUsersList(usersRepository.findAll());
        return null;
    }

    @Override
    public Boolean isEnabled(String userName) {
        Users u = usersRepository.getReferenceById(userName);
        return u.getEnabled();
    }

    @Override
    public Users getUserByUsername(String username) {
        return usersRepository.findById(username).get();
    }


}
