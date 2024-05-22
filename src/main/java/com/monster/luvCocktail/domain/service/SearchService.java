package com.monster.luvCocktail.domain.service;

import com.monster.luvCocktail.domain.entity.Cocktails;
import com.monster.luvCocktail.domain.repository.CocktailsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SearchService {
    @Autowired
    private CocktailsRepository cocktailsRepository;

    public List<Cocktails> findCocktailsByWeatherCode(String weatherCode) {
        return cocktailsRepository.findByrcWeather(weatherCode);
    }
}
