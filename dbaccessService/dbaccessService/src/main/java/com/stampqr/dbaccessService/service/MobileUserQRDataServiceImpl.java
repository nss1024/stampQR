package com.stampqr.dbaccessService.service;

import com.stampqr.dbaccessService.repos.MobileUserQRDataRepository;
import com.stampqr.dbaccessService.resources.MobileUserQRData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MobileUserQRDataServiceImpl implements MobileUserQRDataService{

    @Autowired
    MobileUserQRDataRepository mobileUserQRDataRepository;
    @Override
    public List<MobileUserQRData> getQRCoderForMobileUser(String userName) {
        return mobileUserQRDataRepository.findByUserName(userName);
    }

    @Override
    public List<MobileUserQRData> getMobileUsersOfAQRCode(Long id) {
        return mobileUserQRDataRepository.findByQrDataId(id);
    }

    @Override
    public void saveMobileUserQrDataAssignment(String mobileUserUserName, Long qrDataId) {
        MobileUserQRData mobileUserQRData = new MobileUserQRData();
        mobileUserQRData.setQrDataId(qrDataId);
        mobileUserQRData.setUserName(mobileUserUserName);
        mobileUserQRDataRepository.save(mobileUserQRData);
    }

    @Override
    public MobileUserQRData findByUserNameAndQrDataId(String userName, Long id) {
        return mobileUserQRDataRepository.findByUserNameAndQrDataId(userName, id);
    }

    @Override
    public void deleteMobileUserQrDataAssignment(String userName, Long id) {
        MobileUserQRData muqd = mobileUserQRDataRepository.findByUserNameAndQrDataId(userName,id);
        mobileUserQRDataRepository.delete(muqd);
    }


}
