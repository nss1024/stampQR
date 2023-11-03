/*This class is to provide complete information to the web interface about each user
*in a way that we do not need to call APIs to find out if user is admin or
*whether their account is enabled / disabled
*/

package com.stampQR.webInterface.helperClasses;


import com.stampQR.webInterface.resources.UserDetails;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserDetailsWeb {
    private UserDetails ud;
    private Boolean isActive;
    private Boolean isAdmin;

}
