package com.stampQR.webInterface.WrapperClasses;


import com.stampQR.webInterface.resources.QRData;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class QRDataList {

    private List<QRData> qrDataList;

}
