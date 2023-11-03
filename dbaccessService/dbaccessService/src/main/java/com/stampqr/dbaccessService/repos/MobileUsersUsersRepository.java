package com.stampqr.dbaccessService.repos;

import com.stampqr.dbaccessService.resources.MobileUsersUsers;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MobileUsersUsersRepository extends JpaRepository<MobileUsersUsers,Long> {

    public List<String> findByUserName(String userName);

    public List<String> findByMobileUserName(String mobileUserName);

}
