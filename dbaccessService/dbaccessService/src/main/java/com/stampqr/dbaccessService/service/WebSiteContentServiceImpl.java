package com.stampqr.dbaccessService.service;

import com.stampqr.dbaccessService.repos.WebsiteContentRepository;
import com.stampqr.dbaccessService.resources.WebSiteContent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WebSiteContentServiceImpl implements WebSiteContentService{

    @Autowired
    private WebsiteContentRepository websiteContentRepository;

    @Override
    public boolean saveNewWebContent(String pageName, String sectionName, String sectionComponent,String sectionSubcomponent, String contentText, String imageUrl,Boolean active) {

        boolean success = false;

        WebSiteContent savedWebsiteContent = null;

        WebSiteContent webSiteContent = new WebSiteContent();
        webSiteContent.setPageName(pageName);
        webSiteContent.setSectionName(sectionName);
        webSiteContent.setSectionComponent(sectionComponent);
        webSiteContent.setSectionSubcomponent(sectionSubcomponent);
        webSiteContent.setContentText(contentText);
        webSiteContent.setImageUrl(imageUrl);
        webSiteContent.setContentActive(active);

        savedWebsiteContent = websiteContentRepository.save(webSiteContent);

        success = !savedWebsiteContent.equals(null);

        return success;
    }

    @Override
    public void updateWebContentStatus(boolean b, Long id) {
        WebSiteContent existingWebsiteContent = websiteContentRepository.getReferenceById(id);
        existingWebsiteContent.setContentActive(b);

    }

    @Override
    public List<WebSiteContent> getAllActiveContentByPage(String pageName) {
        List<WebSiteContent> allActiveContentByPage = websiteContentRepository.findWebSiteContentByPageNameAndContentActive(pageName,Boolean.valueOf(true));

        return allActiveContentByPage;
    }

    @Override
    public WebSiteContent getActiveContentByPageAndSection(String pageName, String sectionName) {

        return websiteContentRepository.findWebSiteContentByPageNameAndSectionNameAndContentActive(pageName,sectionName,Boolean.valueOf(true));
    }

    @Override
    public List<WebSiteContent> getAllContentByPage(String pageName) {
        return websiteContentRepository.findWebSiteContentByPageName(pageName);
    }

    public boolean addWebSiteContent(String pageName, String sectionName, String sectionComponent,String sectionSubcomponent ,String contentText,String imageUrl ,Boolean contentActive){

        WebSiteContent savedWsc = new WebSiteContent();
        WebSiteContent wsc = new WebSiteContent();
        wsc.setPageName(pageName);
        wsc.setSectionName(sectionName);
        wsc.setSectionComponent(sectionComponent);
        wsc.setSectionSubcomponent(sectionSubcomponent);
        wsc.setContentText(contentText);
        wsc.setImageUrl(imageUrl);
        wsc.setContentActive(contentActive);

        try{  savedWsc = websiteContentRepository.save(wsc); }
        catch(Exception e){
            return false;
        }

        return !savedWsc.equals(null);


    }

    @Override
    public List<WebSiteContent> getAllContent() {
        return websiteContentRepository.findAll();
    }

    @Override
    public void deleteContent(Long id) {
        WebSiteContent wsc = websiteContentRepository.getReferenceById(id);
        websiteContentRepository.delete(wsc);
    }

    @Override
    public void updateWebsiteContent(Long id, String pageName, String sectionName, String sectionComponent, String sectionSubcomponent, String contentText, String imageUrl, Boolean active) {
        WebSiteContent wsc = websiteContentRepository.getReferenceById(id);
        wsc.setPageName(pageName);
        wsc.setSectionName(sectionName);
        wsc.setSectionComponent(sectionComponent);
        wsc.setSectionSubcomponent(sectionSubcomponent);
        wsc.setContentText(contentText);
        wsc.setImageUrl(imageUrl);
        wsc.setContentActive(active);
        websiteContentRepository.save(wsc);

    }


}
