package com.stampqr.dbaccessService.service;

import com.stampqr.dbaccessService.resources.WebsiteCard;

import java.util.List;

public interface WebsiteCardService {

    public List<WebsiteCard> getAllCardsByPageName(String pageName);

    public List<WebsiteCard> getActiveCardsByPageName(String pageName);

    public List<WebsiteCard> getAllCardsByPageNameAndSectionName(String pageName, String sectionName);

    public List<WebsiteCard> getActiveCardsByPageNameAndSectionName(String pageName, String sectionName);

    public void addNewCard(String pageName, String sectionName, String Image, String title, String text, Boolean active);

    public void updateCard(Long id, String pageName, String sectionName, String Image, String title, String text, Boolean active);

    public void deleteCard(Long id);

    public List<WebsiteCard> getAllCards();

}
