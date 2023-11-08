package com.stampQR.webInterface.services;

import com.stampQR.webInterface.WrapperClasses.AuthoritiesList;
import com.stampQR.webInterface.resources.Authorities;
import com.stampQR.webInterface.resources.SecurityUser;
import com.stampQR.webInterface.resources.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Objects;

@Service
public class SecurityUserDetailsService implements UserDetailsService {


    RestTemplate restTemplate = new RestTemplate();
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println(username);
        String usersURL = "http://localhost:8081/users/getUserByUsername/"+username;
        Users u = restTemplate.getForObject(usersURL, Users.class);

        String authoritiesURL = "http://localhost:8081/authorities/getAuthoritiesByUsername/"+username;
        AuthoritiesList al = restTemplate.getForObject(authoritiesURL, AuthoritiesList.class);

        SecurityUser su = new SecurityUser(u,al.getAuthoritiesList());

        if(Objects.nonNull(u)|Objects.nonNull(al)){
            return su;
        }else{
            throw new UsernameNotFoundException("Username not found : "+username);
        }
    }
}
