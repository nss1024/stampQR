package com.stampQR.webInterface.WrapperClasses;


import com.stampQR.webInterface.resources.WebStatusMessage;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class WebStatusMessageList {

    private List<WebStatusMessage> webStatusMessages;

}
