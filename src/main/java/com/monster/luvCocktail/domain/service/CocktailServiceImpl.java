package com.monster.luvCocktail.domain.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.monster.luvCocktail.domain.dto.CocktailResponse;
import com.monster.luvCocktail.domain.entity.Cocktails;
import com.monster.luvCocktail.domain.repository.CocktailsRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CocktailServiceImpl implements CocktailService {

	private final CocktailsRepository cocktailRepository;
	
	@Override	//readonly = true
	public List<CocktailResponse> getList() {
		// DB에서 전부 찾아서 콜렉터로 모아서 반환
        List<Cocktails> cocktails = cocktailRepository.findAll();
        List<CocktailResponse> response = cocktails.stream()
                .map(cocktail -> new CocktailResponse(
                        cocktail.getId(), cocktail.getName(), cocktail.getCategory(),
                        cocktail.getAlcoholic(), cocktail.getGlass(), cocktail.getInstructions(),
                        cocktail.getCcl(), cocktail.getWeatherId(), cocktail.getIngredient1(),
                        cocktail.getIngredient2(), cocktail.getIngredient3(), cocktail.getMeasure1(),
                        cocktail.getMeasure2(), cocktail.getMeasure3(), cocktail.getPreference(), cocktail.getRecommend()))
                .collect(Collectors.toList());
        return response;
	}

	@Override
	public CocktailResponse getOne(Long cocktailId) {
		Cocktails cocktail = cocktailRepository.findById(cocktailId).orElseThrow();
		CocktailResponse response = new CocktailResponse(
	            cocktail.getId(),
	            cocktail.getName(),
	            cocktail.getCategory(),
	            cocktail.getAlcoholic(),
	            cocktail.getGlass(),
	            cocktail.getInstructions(),
	            cocktail.getCcl(),
	            cocktail.getWeatherId(),
	            cocktail.getIngredient1(),
	            cocktail.getIngredient2(),
	            cocktail.getIngredient3(),
	            cocktail.getMeasure1(),
	            cocktail.getMeasure2(),
	            cocktail.getMeasure3(),
	            cocktail.getPreference(),
				cocktail.getRecommend());
		return response;
	}

}