package com.monster.luvCocktail.domain.dto;

import lombok.Data;

@Data
public class CocktailDTO {
    private Long id;
    private String name;
    private String ingredient1;
    private String ingredient2;
    private String ingredient3;
    private String alcohol_content;
    private Long viewCount; // 조회수 카운트를 저장할 필드
}
