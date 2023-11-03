package com.stampqr.dbaccessService.wrapperClasses;

import com.stampqr.dbaccessService.resources.WebStatusMessage;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.context.annotation.Bean;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class WebStatusMessageList {

    private List<WebStatusMessage> webStatusMessages;

}
