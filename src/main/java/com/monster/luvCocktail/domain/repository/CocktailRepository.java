package com.monster.luvCocktail.domain.repository;

import com.monster.luvCocktail.domain.entity.Cocktail;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import com.monster.luvCocktail.domain.entity.Cocktail;

import java.util.List;

public interface CocktailRepository extends JpaRepository<Cocktail, Long> {
    List<Cocktail> findByRecommendIn(List<String> tasteIds);
    List<Cocktail> findByRcWeather(String weatherCode);
    @EntityGraph(attributePaths = {"views"})
    List<Cocktail> findAll();
}
