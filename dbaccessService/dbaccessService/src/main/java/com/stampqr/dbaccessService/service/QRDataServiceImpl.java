package com.stampqr.dbaccessService.service;

import com.stampqr.dbaccessService.repos.QRDataRepository;
import com.stampqr.dbaccessService.resources.QRData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class QRDataServiceImpl implements QRDataService{

    @Autowired
    private QRDataRepository qrDataRepository;

    @Override
    public boolean saveQRData(QRData qrData) {
        boolean success = false;
        QRData savedQRData = null;
        savedQRData = qrDataRepository.save(qrData);
        return !savedQRData.equals(null);
    }

    @Override
    public QRData getQRDataById(Long id) {

        QRData retrievedQRData = qrDataRepository.findById(id).get();

        return retrievedQRData;
    }

    @Override
    public QRData getQRDataByTag(String tag) {

        QRData retrievedQRData = qrDataRepository.findByQrDataTag(tag);

        return retrievedQRData;
    }

    @Override
    public List<QRData> getQRDataByUserId(Long userId) {

        List<QRData> retrievedQRData = qrDataRepository.findAllByUserIdOrderByCreateDate(userId);

        return retrievedQRData;
    }

    @Override
    public List<QRData> getActiveQRDataByUserId(Long userId) {
        return qrDataRepository.findAllByUserIdAndActive(userId, Boolean.TRUE);
    }

    @Override
    public void deleteQrData(Long id) {
        QRData qrData = qrDataRepository.getReferenceById(id);
        qrDataRepository.delete(qrData);
    }

    @Override
    public void updateQRDataActiveStatus(Long id, Boolean active) {
        QRData qrData = qrDataRepository.getReferenceById(id);
        qrData.setActive(active);
        qrDataRepository.save(qrData);
    }

    @Override
    public List<QRData> findLastThreeByUserIdAndActiveOrderByCreateDate(Long userId) {
        List<QRData> result = new ArrayList<>();
        List<QRData> retrievedQRData = qrDataRepository.findAllByUserIdAndActiveOrderByCreateDateDesc(userId,Boolean.TRUE);

        if(retrievedQRData!=null&retrievedQRData.size()>=3){
            for (int i = 0; i < 3; i++) {
                result.add(retrievedQRData.get(i));
            }
        }

        return result;
    }
}
