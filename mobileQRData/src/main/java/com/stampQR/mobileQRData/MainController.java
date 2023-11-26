package com.stampQR.mobileQRData;
//10.0.1.126:8081
import com.stampQR.mobileQRData.resources.QRData;
import com.stampQR.mobileQRData.utils.NumGCode;
import com.stampQR.mobileQRData.wrapperClasses.QRDataList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.Objects;

@RestController
public class MainController {

    @Autowired
    RestTemplate restTemplate;

    NumGCode numGCode = new NumGCode();

    @GetMapping(value ="/api/v1/getDataByUser/{username}")
    public QRDataList getQRData(@PathVariable("username") String username){


        String qruri = "http://10.0.1.126:8081/qrData/getCodesForMobileUser/"+username;
        QRDataList qrDataList = restTemplate.getForObject(qruri, QRDataList.class);

        if(Objects.nonNull(qrDataList)) {
            for (QRData qrd : qrDataList.getQrDataList()) {
                if(qrd.getEncodedText().length()>0) {
                    String encodedText = qrd.getEncodedText();
                    encodedText=encodedText.replace("G00 ","");
                    encodedText=encodedText.replace("X0","X");
                    encodedText=encodedText.replace("Y0","Y");
                    encodedText=encodedText.replace("Z0","Z");
                    String gCodeArray[] = encodedText.split("\n");

                    numGCode.generateNumericGCode(gCodeArray);
                    float floarArray[] = numGCode.getNumbericGCode();
                    StringBuilder sb = new StringBuilder();
                    for (float f:floarArray) {
                        sb.append(f);
                        sb.append(";");
                    }
                    qrd.setEncodedText(sb.toString());
                }
            }
        }
        return  qrDataList;

    }

    @GetMapping(value="/api/gcode/getAllDataByUserId/{userId}")
    public QRDataList getQRDataByUserId(@PathVariable("userId") Long id){

        String qruri = "http://10.0.1.126:8081/qrData/getAllDataByUserId/"+id;
        QRDataList qrDataList = restTemplate.getForObject(qruri, QRDataList.class);

        return  qrDataList;
    }


}
