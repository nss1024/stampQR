package com.stampQR.webInterface.WrapperClasses;


import com.stampQR.webInterface.resources.MobileUserQRData;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class QRDataAssignmentList {

    List<MobileUserQRData> mobileUserQRDataList;

}
