package com.monster.luvCocktail.domain.repository;

import com.monster.luvCocktail.domain.entity.Cocktails;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CocktailsRepository extends JpaRepository<Cocktails, Long> {
    List<Cocktails> findByRecommendIn(List<String> tasteIds);
}