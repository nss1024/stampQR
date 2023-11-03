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

public class WebStatusMessage {


    private Long id;

    private String statusTitle;

    private Boolean statusActive;

    private String statusMessage;

    private LocalDateTime statusCreateDate;



}
