package com.stampqr.dbaccessService.wrapperClasses;

import com.stampqr.dbaccessService.resources.UserDetails;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserDetailsList {

    List<UserDetails> userDetailsList;

}
