package com.stampQR.webInterface.helperClasses;

import com.stampQR.webInterface.resources.MobileUserDetails;
import com.stampQR.webInterface.resources.UserDetails;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserAssignment {

    private Boolean userAssigned;
    private MobileUserDetails mobileUserDetails;

}

