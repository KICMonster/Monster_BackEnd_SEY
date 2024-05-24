package com.monster.luvCocktail.domain.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
public class Cocktails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CK_ID")
    private Long id;
    
    @Column(name = "CK_NAME")
    private String name;
    
    @Column(name = "CK_CATEGORY")
    private String category;
    
    @Column(name = "CK_ALCOHOLIC")
    private String alcoholic;
    
    @Column(name = "CK_GLASS")
    private String glass;
    
    @Column(name = "CK_INSTRUCTIONS")
    private String instructions;
    
    @Column(name = "CCL_CHECK_STATUS")
    private String ccl;
    
    @Column(name = "RC_WEATHER_ID")
    private String weatherId;
    
    @Column(name = "CK_INGREDIENT1")
    private String ingredient1;

    @Column(name = "CK_INGREDIENT2")
    private String ingredient2;

    @Column(name = "CK_INGREDIENT3")
    private String ingredient3;
    
    @Column(name = "CK_MEASURE1")
    private String measure1;
    
    @Column(name = "CK_MEASURE2")
    private String measure2;
    
    @Column(name = "CK_MEASURE3")
    private String measure3;
    
    @Column(name = "CK_PREFERENCE")
    private String preference;
    
    @Column(name = "CK_RECOMMEND")
    private String recommend;
    
    private String rcWeather;


    @OneToMany(mappedBy = "cocktail", cascade = CascadeType.ALL)
    private List<View> views = new ArrayList<>();
}
