package com.stampqr.dbaccessService.resources;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "web_status_message")
public class WebStatusMessage {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "status_title")
    private String statusTitle;
    @Column(name = "status_active")
    private Boolean statusActive;
    @Column(name = "status_message")
    private String statusMessage;
    @Column(name = "status_create_date")
    private LocalDateTime statusCreateDate;



}
