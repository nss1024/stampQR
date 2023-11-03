package com.stampqr.dbaccessService.service;

import com.stampqr.dbaccessService.wrapperClasses.WebStatusMessageList;

public interface WebStatusMessageService {

    public boolean addNewStatusMessage(String statusTitle, Boolean statusActive, String statusMessage);

    public void updateStatusMessage(Long statusId, String title, String message, Boolean active);

    public Long getStatusIdByTitle(String statusTitle);

    public WebStatusMessageList getActiveStatusMessages();

    public void deleteStatusMessage(Long id);

}
