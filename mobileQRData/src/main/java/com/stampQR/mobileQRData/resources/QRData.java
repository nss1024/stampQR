package com.stampQR.mobileQRData.resources;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter

public class QRData {
    private Long qrDataId;
    private String plainText;
    private String encodedText;
    private String errorCorrectionLevel;
    private String qrDataTag;
    private String imageURL;
    private Long ver;
    private Long userId;
    private LocalDateTime createDate;
    private Boolean active;

}
