package com.stampqr.dbaccessService.resources;

import jakarta.persistence.*;
import lombok.*;
import org.checkerframework.common.aliasing.qual.Unique;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "web_site_content")
public class WebSiteContent {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "page_name")
    private String pageName;
    @Column(name = "section_name")
    private String sectionName;
    @Column(name = "section_component", unique = true)
    private String sectionComponent;
    @Column(name = "section_subcomponent", unique = true)
    private String sectionSubcomponent;
    @Column(name = "content_text")
    private String contentText;
    @Column(name = "image_url")
    private String imageUrl;
    @Column(name = "content_active")
    private Boolean contentActive;
    @Column(name = "content_create_date",insertable = false,updatable = false)
    private LocalDateTime contentCreateDate;


}
