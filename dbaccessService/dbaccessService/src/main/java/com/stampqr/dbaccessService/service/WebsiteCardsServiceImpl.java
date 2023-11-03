package com.stampqr.dbaccessService.service;

import com.stampqr.dbaccessService.repos.WebsiteCardRepository;
import com.stampqr.dbaccessService.resources.WebsiteCard;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WebsiteCardsServiceImpl implements WebsiteCardService{

    @Autowired
    WebsiteCardRepository websiteCardRepository;


    @Override
    public List<WebsiteCard> getAllCardsByPageName(String pageName) {
        return websiteCardRepository.getWebsiteCardByPageName(pageName);
    }

    @Override
    public List<WebsiteCard> getActiveCardsByPageName(String pageName) {
        return websiteCardRepository.getWebsiteCardByPageNameAndActive(pageName, Boolean.TRUE);
    }

    @Override
    public List<WebsiteCard> getAllCardsByPageNameAndSectionName(String pageName, String sectionName) {
        return websiteCardRepository.getWebsiteCardByPageNameAndSectionName(pageName,sectionName);
    }

    @Override
    public List<WebsiteCard> getActiveCardsByPageNameAndSectionName(String pageName, String sectionName) {
        return websiteCardRepository.getWebsiteCardByPageNameAndSectionNameAndActive(pageName,sectionName, Boolean.TRUE);
    }

    @Override
    public void addNewCard(String pageName, String sectionName, String image, String title, String text, Boolean active) {
        WebsiteCard wsc = new WebsiteCard();
        wsc.setPageName(pageName);
        wsc.setSectionName(sectionName);
        wsc.setImage(image);
        wsc.setTitle(title);
        wsc.setText(text);
        wsc.setActive(active);
        websiteCardRepository.save(wsc);
    }

    public void updateCard(Long id, String pageName, String sectionName, String image, String title, String text, Boolean active) {
        WebsiteCard wsc = websiteCardRepository.getReferenceById(id);
        wsc.setPageName(pageName);
        wsc.setSectionName(sectionName);
        wsc.setImage(image);
        wsc.setTitle(title);
        wsc.setText(text);
        wsc.setActive(active);
        websiteCardRepository.save(wsc);
    }

    @Override
    public void deleteCard(Long id) {
        WebsiteCard wsc = websiteCardRepository.getReferenceById(id);
        websiteCardRepository.delete(wsc);
    }

    @Override
    public List<WebsiteCard> getAllCards() {
        return websiteCardRepository.findAll();
    }


}
