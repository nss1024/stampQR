package com.stampqr.dbaccessService.resources;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "web_site_cards")
public class WebsiteCard {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "page_name")
    private String pageName;
    @Column(name = "section_name")
    private String sectionName;
    @Column(name = "image")
    private String image;
    @Column(name = "title")
    private String title;
    @Column(name = "text")
    private String text;
    @Column(name = "content_active")
    private Boolean active;
    @Column(name = "content_create_date",insertable = false,updatable = false)
    private LocalDateTime contentCreateDate;


}
