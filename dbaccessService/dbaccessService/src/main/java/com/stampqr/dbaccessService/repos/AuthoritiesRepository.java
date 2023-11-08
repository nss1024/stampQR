package com.stampqr.dbaccessService.repos;


import com.stampqr.dbaccessService.resources.Authorities;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AuthoritiesRepository extends JpaRepository<Authorities,String> {



    public List<Authorities> getAuthoritiesByUsername(String userName);

    public Authorities getAuthorityByUsername(String userName);

    public Authorities findByUsernameAndAuthority(String username, String authority);

}
