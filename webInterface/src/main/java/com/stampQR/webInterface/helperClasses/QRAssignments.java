package com.stampQR.webInterface.helperClasses;

import com.stampQR.webInterface.WrapperClasses.MobileUsersDetailsWraper;
import com.stampQR.webInterface.WrapperClasses.QRDataAssignmentList;
import com.stampQR.webInterface.resources.MobileUserDetails;
import com.stampQR.webInterface.resources.MobileUserQRData;
import com.stampQR.webInterface.resources.QRData;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class QRAssignments {

    QRData qrData;
    List<UserAssignment> assignedUsersList;


    public void generateAssignedUsersList (QRDataAssignmentList qal, MobileUsersDetailsWraper mudw){

        for (MobileUserDetails mu:mudw.getMobileUserDetailsList()) {
            for (MobileUserQRData qrAssignment: qal.getMobileUserQRDataList()) {
                if(mu.getMobileUsername().equals(qrAssignment.getUserName())){
                    assignedUsersList.add(new UserAssignment(Boolean.TRUE,mu));
                }else{
                    assignedUsersList.add(new UserAssignment(Boolean.FALSE,mu));
                }
            }
        }

    }

}
