package com.monster.luvCocktail.domain.gis.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.monster.luvCocktail.domain.gis.dto.GisRequest;
import com.monster.luvCocktail.domain.gis.dto.SearchResponse;
import com.monster.luvCocktail.domain.gis.service.GisService;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/gis")
public class GisController {

	private final GisService gisService;

	@GetMapping("/get")
	public ResponseEntity<SearchResponse> create(@RequestParam("latitude") double latitude, @RequestParam("longitude") double longitude) throws Exception {
		return gisService.getPlaceInfo(latitude, longitude);
	}
}