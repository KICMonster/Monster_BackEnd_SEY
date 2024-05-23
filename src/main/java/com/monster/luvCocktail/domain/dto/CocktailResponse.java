package com.monster.luvCocktail.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CocktailResponse {
    private Long id;
    private String name;
    private String category;
    private String alcoholic;
    private String glass;
    private String instructions;
    private String ccl;
    private String weatherId;
    private String ingredient1;
    private String ingredient2;
    private String ingredient3;
    private String measure1;
    private String measure2;
    private String measure3;
    private String preference;
    private String recommend;
}
