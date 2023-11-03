package com.qrstamp.core.generator;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.awt.*;
import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter

public class QRRequestresponseObject implements Serializable {
    private String textToEncode; // what has been encoded into QR Code
    private String encodedText; // the encoded String
    private int version;// qr code version the text is encoded in

    private String img; // qr code image url
    private String error;

}
