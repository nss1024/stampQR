package com.stampQR.mobileQRData.repos;



import com.stampQR.mobileQRData.resources.MobileUsers;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MobileUsersRepository extends JpaRepository<MobileUsers,String> {

    public MobileUsers getMobileUsersByUsername(String username);

}
