package com.stampqr.dbaccessService.repos;


import com.stampqr.dbaccessService.resources.UserDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDetailsRepository extends JpaRepository<UserDetails,Long> {

    @Query(value = "SELECT * FROM USERDETAILS WHERE username = ?1", nativeQuery = true)
    public UserDetails getUserDetailsByUserName(String userName);

}
