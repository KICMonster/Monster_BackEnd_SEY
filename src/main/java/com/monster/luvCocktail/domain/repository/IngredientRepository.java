package com.monster.luvCocktail.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.monster.luvCocktail.domain.entity.Snack;

import java.util.List;

public interface IngredientRepository extends JpaRepository<Snack, Long> {
}
