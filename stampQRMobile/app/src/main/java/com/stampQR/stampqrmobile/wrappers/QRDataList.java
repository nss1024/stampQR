package com.stampQR.stampqrmobile.wrappers;


import com.stampQR.stampqrmobile.model.QRData;

import java.io.Serializable;
import java.util.List;

public class QRDataList implements Serializable {

    private List<QRData> qrDataList;


    public QRDataList(List<QRData> qrDataList) {
        this.qrDataList = qrDataList;
    }

    public QRDataList() {

    }

    public List<QRData> getQrDataList() {
        return qrDataList;
    }

    public void setQrDataList(List<QRData> qrDataList) {
        this.qrDataList = qrDataList;
    }
}
