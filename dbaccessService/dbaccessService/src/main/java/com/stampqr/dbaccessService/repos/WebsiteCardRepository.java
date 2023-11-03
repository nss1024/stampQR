package com.stampqr.dbaccessService.repos;

import com.stampqr.dbaccessService.resources.WebsiteCard;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WebsiteCardRepository extends JpaRepository<WebsiteCard, Long> {

    public List<WebsiteCard> getWebsiteCardByPageName(String pageName);

    public List<WebsiteCard> getWebsiteCardByPageNameAndActive(String pageName, Boolean active);

    public List<WebsiteCard> getWebsiteCardByPageNameAndSectionName(String pageName, String sectionName);

    public List<WebsiteCard> getWebsiteCardByPageNameAndSectionNameAndActive(String pageName, String sectionName, Boolean active);



}
