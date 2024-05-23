package com.monster.luvCocktail.domain.service;

import java.util.List;

import com.monster.luvCocktail.domain.dto.CocktailResponse;

public interface CocktailService {
	// 칵테일 생성하기
//	CocktailResponseDTO create(CocktailRequest request);

	List<CocktailResponse> getList();
	
	CocktailResponse getOne(Long cocktailId);
}