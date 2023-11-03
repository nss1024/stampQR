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

public class FAQ {


    private Long id;
    private String question;
    private String answer;
    private String category;
    private Boolean active;
    private LocalDateTime createDate;
}
