package com.stampqr.dbaccessService.repos;

import com.stampqr.dbaccessService.resources.WebSiteContent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WebsiteContentRepository extends JpaRepository<WebSiteContent,Long> {

    public List<WebSiteContent> findWebSiteContentByPageNameAndContentActive(String pageName, Boolean b);

    public WebSiteContent findWebSiteContentByPageNameAndSectionNameAndContentActive(String pageName, String sectionName,Boolean b);

    public List<WebSiteContent> findWebSiteContentByPageName(String pageName);



}
