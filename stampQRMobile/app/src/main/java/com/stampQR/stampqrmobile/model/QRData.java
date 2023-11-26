package com.stampQR.stampqrmobile.model;

import java.io.Serializable;
import java.time.LocalDateTime;


public class QRData implements Serializable {
    private Long qrDataId;
    private String plainText;
    private String encodedText;
    private String errorCorrectionLevel;
    private String qrDataTag;
    private String imageURL;
    private Long ver;
    private Long userId;
    private String createDate;
    private Boolean active;

    public QRData(Long qrDataId, String plainText, String encodedText, String errorCorrectionLevel, String qrDataTag, String imageURL, Long ver, Long userId, String createDate, Boolean active) {
        this.qrDataId = qrDataId;
        this.plainText = plainText;
        this.encodedText = encodedText;
        this.errorCorrectionLevel = errorCorrectionLevel;
        this.qrDataTag = qrDataTag;
        this.imageURL = imageURL;
        this.ver = ver;
        this.userId = userId;
        this.createDate = createDate;
        this.active = active;
    }

    public QRData() {
    }

    public Long getQrDataId() {
        return qrDataId;
    }

    public String getPlainText() {
        return plainText;
    }

    public String getEncodedText() {
        return encodedText;
    }

    public String getErrorCorrectionLevel() {
        return errorCorrectionLevel;
    }

    public String getQrDataTag() {
        return qrDataTag;
    }

    public String getImageURL() {
        return imageURL;
    }

    public Long getVer() {
        return ver;
    }

    public Long getUserId() {
        return userId;
    }

    public String getCreateDate() {
        return createDate;
    }

    public Boolean getActive() {
        return active;
    }

    public void setQrDataId(Long qrDataId) {
        this.qrDataId = qrDataId;
    }

    public void setPlainText(String plainText) {
        this.plainText = plainText;
    }

    public void setEncodedText(String encodedText) {
        this.encodedText = encodedText;
    }

    public void setErrorCorrectionLevel(String errorCorrectionLevel) {
        this.errorCorrectionLevel = errorCorrectionLevel;
    }

    public void setQrDataTag(String qrDataTag) {
        this.qrDataTag = qrDataTag;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public void setVer(Long ver) {
        this.ver = ver;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    @Override
    public String toString() {
        return "QRData{" +
                "qrDataId=" + qrDataId +
                ", plainText='" + plainText + '\'' +
                ", encodedText='" + encodedText + '\'' +
                ", errorCorrectionLevel='" + errorCorrectionLevel + '\'' +
                ", qrDataTag='" + qrDataTag + '\'' +
                ", imageURL='" + imageURL + '\'' +
                ", ver=" + ver +
                ", userId=" + userId +
                ", createDate=" + createDate +
                ", active=" + active +
                '}';
    }
}
