package com.monster.luvCocktail.domain.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

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
    private String rcWeather;


    @OneToMany(mappedBy = "cocktail", cascade = CascadeType.ALL)
    private List<View> views = new ArrayList<>();
}
