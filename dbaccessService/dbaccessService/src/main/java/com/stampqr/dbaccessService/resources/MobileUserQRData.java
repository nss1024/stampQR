package com.stampqr.dbaccessService.resources;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name="mobile_user_qr_data")
public class MobileUserQRData {

    @Id@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Long id;
    @Column(name="qrDataId")
    private Long qrDataId;
    @Column(name="username")
    private String userName;

}
