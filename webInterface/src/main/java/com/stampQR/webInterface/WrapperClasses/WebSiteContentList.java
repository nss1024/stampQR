package com.stampQR.webInterface.WrapperClasses;

import com.stampQR.webInterface.resources.WebSiteContent;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class WebSiteContentList {

    private List<WebSiteContent> webSiteContentList;

}
