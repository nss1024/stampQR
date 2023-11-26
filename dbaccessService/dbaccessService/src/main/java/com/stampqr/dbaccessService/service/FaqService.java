package com.stampqr.dbaccessService.service;

import com.stampqr.dbaccessService.resources.FAQ;

import java.util.List;

public interface FaqService {

    public void createNewFaq(String question, String answer,
                             String category, Boolean active);

    public void updateFaq(Long id, String question, String answer,
                          String category, Boolean active);

    public void deleteFaq(Long id);

    public List<FAQ> getActiveFaq();

    public List<FAQ> getAllFaqs();

}
