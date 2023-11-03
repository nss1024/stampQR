package com.stampqr.dbaccessService.wrapperClasses;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class MobileUserQRDataWrapper {

    private List<Long> qrCodeForMobileUser;

    private List<String> usersOfQRCode;

}
