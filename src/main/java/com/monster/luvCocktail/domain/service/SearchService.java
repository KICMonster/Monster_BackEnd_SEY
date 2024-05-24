package com.monster.luvCocktail.domain.service;

import com.monster.luvCocktail.domain.dto.CocktailDTO;
import com.monster.luvCocktail.domain.entity.Cocktails;
import com.monster.luvCocktail.domain.repository.CocktailsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SearchService {
    @Autowired
    private CocktailsRepository cocktailsRepository;

    public List<Cocktails> findCocktailsByWeatherCode(String weatherCode) {
        return cocktailsRepository.findByrcWeather(weatherCode);
    }
    public List<CocktailDTO> getAllCocktails() {
        List<Cocktails> cocktails = cocktailsRepository.findAll();
        return cocktails.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    private CocktailDTO convertToDTO(Cocktails cocktail) {
        CocktailDTO dto = new CocktailDTO();
        dto.setId(cocktail.getId());
        dto.setName(cocktail.getName());
        dto.setIngredient1(cocktail.getIngredient1());
        dto.setIngredient2(cocktail.getIngredient2());
        dto.setIngredient3(cocktail.getIngredient3());
        dto.setAlcohol_content(cocktail.getAlcohol_content());
        // 조회수 설정
        dto.setViewCount((long) cocktail.getViews().size());
        // 나머지 필드도 동일하게 설정해줄 수 있습니다.
        return dto;
    }
}
