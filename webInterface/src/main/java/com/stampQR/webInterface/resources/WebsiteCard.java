package com.stampQR.webInterface.resources;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter

public class WebsiteCard {
    private Long id;
    private String pageName;
    private String sectionName;
    private String image;
    private String title;
    private String text;
    private Boolean active;
    private LocalDateTime contentCreateDate;


}
