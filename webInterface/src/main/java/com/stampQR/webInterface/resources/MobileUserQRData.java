package com.stampQR.webInterface.resources;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter

public class MobileUserQRData {

    private Long id;
    private Long qrDataId;
    private String userName;

}
