package com.stampqr.dbaccessService.resources;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name="faq")
public class FAQ {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="id")
    private Long id;
    @Column(name="question")
    private String question;
    @Column(name="answer")
    private String answer;
    @Column(name="category")
    private String category;
    @Column(name="content_active")
    private Boolean active;
    @Column(name = "create_date",insertable = false,updatable = false)
    private LocalDateTime createDate;
}
