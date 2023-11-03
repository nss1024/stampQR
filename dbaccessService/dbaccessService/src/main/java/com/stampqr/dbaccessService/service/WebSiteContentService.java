package com.stampqr.dbaccessService.service;

import com.stampqr.dbaccessService.resources.WebSiteContent;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

public interface WebSiteContentService {

    public boolean saveNewWebContent(String pageName, String sectionName, String sectionComponent,String sectionSubcomponent, String contentText, String imageUrl,Boolean active);

    public void updateWebContentStatus(boolean b, Long id);

    public List<WebSiteContent> getAllActiveContentByPage(String pageName);

    public WebSiteContent getActiveContentByPageAndSection(String pageName, String sectionName);

    public List<WebSiteContent> getAllContentByPage(String pageName);

    public List<WebSiteContent> getAllContent();

    public void deleteContent(Long id);

    public void updateWebsiteContent(Long id, String pageName, String sectionName, String sectionComponent,String sectionSubcomponent, String contentText, String imageUrl,Boolean active);

}
