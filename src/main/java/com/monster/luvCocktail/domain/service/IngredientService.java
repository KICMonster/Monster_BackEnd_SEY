package com.monster.luvCocktail.domain.service;

import java.util.List;

import com.monster.luvCocktail.domain.dto.CocktailResponse;
import com.monster.luvCocktail.domain.dto.IngredientResponse;

public interface IngredientService {
	// 칵테일 생성하기
//	CocktailResponseDTO create(CocktailRequest request);

	List<IngredientResponse> getList();
	
	IngredientResponse getOne(Long ingredientId);
}