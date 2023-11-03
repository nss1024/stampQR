package com.stampqr.dbaccessService.wrapperClasses;

import com.stampqr.dbaccessService.resources.WebSiteContent;
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
