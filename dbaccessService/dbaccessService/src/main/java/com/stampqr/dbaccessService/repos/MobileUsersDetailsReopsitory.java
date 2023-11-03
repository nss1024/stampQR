package com.stampqr.dbaccessService.repos;

import com.stampqr.dbaccessService.resources.MobileUserDetails;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MobileUsersDetailsReopsitory extends JpaRepository<MobileUserDetails,Long> {

    public List<MobileUserDetails> getMobileUserDetailsByCreatingUserId(Long creatingUserId);

}
