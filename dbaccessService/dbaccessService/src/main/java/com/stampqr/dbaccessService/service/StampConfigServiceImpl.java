package com.stampqr.dbaccessService.service;

import com.stampqr.dbaccessService.repos.StampConfigRepository;
import com.stampqr.dbaccessService.resources.StampConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StampConfigServiceImpl implements StampConfigService{

    @Autowired
    StampConfigRepository stampConfigRepository;

    @Override
    public StampConfig getStampConfig(Long id) {
        return stampConfigRepository.findById(id).get();
    }
}
