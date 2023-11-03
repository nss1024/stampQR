package com.stampqr.dbaccessService.repos;


import com.stampqr.dbaccessService.resources.MobileAuthorities;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MobileAuthoritiesRepository extends JpaRepository<MobileAuthorities, String> {

    public void deleteByUsername(String username);

}
