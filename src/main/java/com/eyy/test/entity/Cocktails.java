package com.eyy.test.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class Cocktails {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "Coctails_seq")
    private Long id;
    private String name;
    private String ingredient1;
    private String ingredient2;
    private String ingredient3;
    private String alcohol_content;
    private String recommend;
}
