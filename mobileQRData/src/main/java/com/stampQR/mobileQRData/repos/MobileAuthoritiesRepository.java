package com.stampQR.mobileQRData.repos;



import com.stampQR.mobileQRData.resources.MobileAuthorities;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MobileAuthoritiesRepository extends JpaRepository<MobileAuthorities, String> {

    public void deleteByUsername(String username);

    public List<MobileAuthorities> findByUsername(String username);

}
