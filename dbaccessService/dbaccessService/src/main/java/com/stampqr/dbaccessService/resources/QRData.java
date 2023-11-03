package com.stampqr.dbaccessService.resources;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name="qr_data")
public class QRData {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="qrDataId")
    private Long qrDataId;
    @Column(name="plaintext")
    private String plainText;
    @Column(name="encodedtext")
    private String encodedText;
    @Column(name="errorCorrectionLevel")
    private String errorCorrectionLevel;
    @Column(name="qrDataTag")
    private String qrDataTag;
    @Column(name="imageURL")
    private String imageURL;
    @Column(name="ver")
    private Long ver;
    @Column(name="userId")
    private Long userId;
    @Column(name = "createDate",insertable = false,updatable = false)
    private LocalDateTime createDate;
    @Column(name = "active")
    private Boolean active;

}
