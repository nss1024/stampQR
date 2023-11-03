package com.stampQR.mobileQRData;

import com.stampQR.mobileQRData.wrapperClasses.QRDataList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class MainController {

    @Autowired
    RestTemplate restTemplate;

    @GetMapping(value ="/api/v1/getDataByUser/{username}")
    public QRDataList getQRData(@PathVariable("username") String username){

        String qruri = "http://localhost:8081/qrData/getCodesForMobileUser/"+username;
        QRDataList qrDataList = restTemplate.getForObject(qruri, QRDataList.class);

        return  qrDataList;

    }


}
