package com.monster.luvCocktail.domain.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.monster.luvCocktail.domain.dto.CocktailResponse;
import com.monster.luvCocktail.domain.dto.IngredientResponse;
import com.monster.luvCocktail.domain.service.CocktailService;
import com.monster.luvCocktail.domain.service.IngredientService;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@RestController
@CrossOrigin(origins = "https://localhost:5174")
@Log4j2
@RequiredArgsConstructor
@RequestMapping("/api/ingredient") // 고유한 경로로 수정
public class IngredientController {
	
	private final IngredientService ingredientService;

	@GetMapping("")
	public ResponseEntity<List<IngredientResponse>> getList() {
		log.info("get Ingredient List 메서드 실행");
		return ResponseEntity.ok(ingredientService.getList());
	}
}
