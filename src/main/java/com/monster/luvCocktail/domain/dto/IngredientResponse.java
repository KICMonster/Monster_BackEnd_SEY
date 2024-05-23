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
public class IngredientResponse {
    private Long id;
    private String name;
    private String image;
    private String category;
    private String description;
}
