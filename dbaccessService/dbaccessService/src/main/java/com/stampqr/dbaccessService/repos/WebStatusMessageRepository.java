package com.stampqr.dbaccessService.repos;

import com.stampqr.dbaccessService.resources.WebStatusMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WebStatusMessageRepository extends JpaRepository<WebStatusMessage,Long> {

    public WebStatusMessage getWebStatusMessageByStatusTitle(String statusTitle);

    public List<WebStatusMessage> findAllByStatusActive(Boolean b);

}
