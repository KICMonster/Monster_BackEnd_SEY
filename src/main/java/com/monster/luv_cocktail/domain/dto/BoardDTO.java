package com.monster.luv_cocktail.domain.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
public class BoardDTO {

    private Long id;
    private String title;
    private String content;
    private Date createdAt;
    private Date updatedAt;
    private String memberId; // Member의 ID만 포함
    private Long customCocktailId; // CustomCocktail의 ID만 포함
}
