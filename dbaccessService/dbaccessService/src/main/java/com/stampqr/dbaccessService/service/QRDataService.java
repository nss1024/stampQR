package com.stampqr.dbaccessService.service;


import com.stampqr.dbaccessService.resources.QRData;

import java.util.List;

public interface QRDataService {

    public boolean saveQRData(QRData qrData);

    public QRData getQRDataById(Long id);

    public QRData getQRDataByTag(String tag);

    public List<QRData> getQRDataByUserId(Long userId);

    public List<QRData> getActiveQRDataByUserId(Long userId);

    public void deleteQrData(Long id);

    public void updateQRDataActiveStatus(Long id, Boolean active);

    public List<QRData> findLastThreeByUserIdAndActiveOrderByCreateDate(Long userId);
}
