package com.stampQR.webInterface.resources;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter

public class WebSiteContent {
    private Long id;
    private String pageName;
    private String sectionName;
    private String sectionComponent;
    private String sectionSubcomponent;
    private String contentText;
    private String imageUrl;
    private Boolean contentActive;
    private LocalDateTime contentCreateDate;
}
