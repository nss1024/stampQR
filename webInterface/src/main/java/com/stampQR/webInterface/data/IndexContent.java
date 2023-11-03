package com.stampQR.webInterface.data;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Getter
@Setter
public class IndexContent {

    private String sectionName;
    private String sectionContent;
    private String sectionImage;


}
