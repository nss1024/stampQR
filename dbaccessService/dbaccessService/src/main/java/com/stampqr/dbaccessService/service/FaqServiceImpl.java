package com.stampqr.dbaccessService.service;

import com.stampqr.dbaccessService.repos.FaqRepository;
import com.stampqr.dbaccessService.resources.FAQ;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FaqServiceImpl implements FaqService{

    @Autowired
    private FaqRepository faqRepository;
    @Override
    public void createNewFaq(String question, String answer, String category, Boolean active) {
        FAQ faq = new FAQ();
        faq.setQuestion(question);
        faq.setAnswer(answer);
        faq.setCategory(category);
        faq.setActive(active);
        faqRepository.save(faq);
    }

    @Override
    public void updateFaq(Long id, String question, String answer, String category, Boolean active) {
        FAQ faq = faqRepository.getReferenceById(id);
        faq.setQuestion(question);
        faq.setAnswer(answer);
        faq.setCategory(category);
        faq.setActive(active);

        faqRepository.save(faq);

    }

    @Override
    public void deleteFaq(Long id) {
        FAQ faq = faqRepository.getReferenceById(id);
        faqRepository.delete(faq);
    }

    @Override
    public List<FAQ> getActiveFaq() {
        return faqRepository.findByActive(Boolean.TRUE);
    }

    @Override
    public List<FAQ> getAllFaqs() {
        return faqRepository.findAll();
    }


}
