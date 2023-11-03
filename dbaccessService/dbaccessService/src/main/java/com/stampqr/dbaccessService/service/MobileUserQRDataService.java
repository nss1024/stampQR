package com.stampqr.dbaccessService.service;

import com.stampqr.dbaccessService.resources.MobileUserDetails;
import com.stampqr.dbaccessService.resources.MobileUserQRData;

import java.util.List;

public interface MobileUserQRDataService {

    public List<MobileUserQRData> getQRCoderForMobileUser(String userName);

    public List<MobileUserQRData> getMobileUsersOfAQRCode(Long id);

    public void saveMobileUserQrDataAssignment(String mobileUserUserName, Long qrDataId);

    public MobileUserQRData findByUserNameAndQrDataId(String userName, Long id);

    public void deleteMobileUserQrDataAssignment (String userName, Long id);

}
