package com.stampqr.dbaccessService.repos;


import com.stampqr.dbaccessService.resources.MobileUsers;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MobileUsersRepository extends JpaRepository<MobileUsers,String> {

    public MobileUsers getMobileUsersByUsername(String username);

}
