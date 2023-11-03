package com.stampqr.dbaccessService.wrapperClasses;

import com.stampqr.dbaccessService.resources.Users;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UsersList {

    List<Users> usersList;

}
