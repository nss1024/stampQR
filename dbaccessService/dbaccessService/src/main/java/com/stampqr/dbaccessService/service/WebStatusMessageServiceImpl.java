package com.stampqr.dbaccessService.service;

import com.stampqr.dbaccessService.repos.WebStatusMessageRepository;
import com.stampqr.dbaccessService.resources.WebStatusMessage;
import com.stampqr.dbaccessService.wrapperClasses.WebStatusMessageList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WebStatusMessageServiceImpl implements WebStatusMessageService{

    @Autowired
    private WebStatusMessageRepository webStatusMessageRepository;

    @Override
    public boolean addNewStatusMessage(String statusTitle, Boolean statusActive, String statusMessage) {

        WebStatusMessage savedWsm = null;
        WebStatusMessage wsm = new WebStatusMessage();
        wsm.setStatusTitle(statusTitle);
        wsm.setStatusActive(statusActive);
        wsm.setStatusMessage(statusMessage);

        savedWsm = webStatusMessageRepository.save(wsm);

        return !savedWsm.equals(null);
    }


    @Override
    public Long getStatusIdByTitle(String statusTitle) {
        Long id = Long.valueOf(0);

        try{
            WebStatusMessage wsm = webStatusMessageRepository.getWebStatusMessageByStatusTitle(statusTitle);
            id=wsm.getId();

        }catch(Exception e){
        }
        return id;
    }

    @Override
    public WebStatusMessageList getActiveStatusMessages() {
        WebStatusMessageList webStatusMessageList = new WebStatusMessageList();
        webStatusMessageList.setWebStatusMessages(webStatusMessageRepository.findAllByStatusActive(Boolean.TRUE));
        return webStatusMessageList;
    }

    @Override
    public void deleteStatusMessage(Long id) {
        WebStatusMessage wsm = webStatusMessageRepository.getReferenceById(id);
        webStatusMessageRepository.delete(wsm);
    }

    public void updateStatusMessage(Long statusId, String title, String message, Boolean active) {
        WebStatusMessage wsm =  webStatusMessageRepository.getReferenceById(statusId);
            wsm.setStatusTitle(title);
            wsm.setStatusMessage(message);
            wsm.setStatusActive(active);
            webStatusMessageRepository.save(wsm);
    }

    public List<WebStatusMessage> getAllStatusMessages(){

        return webStatusMessageRepository.findAll();

    }

}
