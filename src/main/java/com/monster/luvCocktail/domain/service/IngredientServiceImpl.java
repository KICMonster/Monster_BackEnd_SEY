package com.monster.luvCocktail.domain.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.monster.luvCocktail.domain.dto.CocktailResponse;
import com.monster.luvCocktail.domain.dto.IngredientResponse;
import com.monster.luvCocktail.domain.entity.Cocktails;
import com.monster.luvCocktail.domain.entity.Ingredient;
import com.monster.luvCocktail.domain.repository.IngredientRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class IngredientServiceImpl implements IngredientService {

	private final IngredientRepository ingredientRepository;
	
	@Override	//readonly = true
	public List<IngredientResponse> getList() {
		// DB에서 전부 찾아서 콜렉터로 모아서 반환
        List<Ingredient> ingredients = ingredientRepository.findAll();
        List<IngredientResponse> response = ingredients.stream()
                .map(ingredient -> new IngredientResponse(
                        ingredient.getId(), ingredient.getName(), ingredient.getCategory(),
                        ingredient.getDescription(), ingredient.getImage()))
                .collect(Collectors.toList());
        return response;
	}

	@Override
	public IngredientResponse getOne(Long ingredientId) {
		
		Ingredient ingredient = ingredientRepository.findById(ingredientId).orElseThrow();
		IngredientResponse response = new IngredientResponse(
				ingredient.getId(),
				ingredient.getName(),
				ingredient.getImage(),
				ingredient.getCategory(),
				ingredient.getDescription()
				);
				
		return response;
	}
 

}