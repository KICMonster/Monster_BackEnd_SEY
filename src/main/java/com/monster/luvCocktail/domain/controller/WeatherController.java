package com.monster.luvCocktail.domain.controller;

import com.monster.luvCocktail.domain.dto.WeatherDTO;
import com.monster.luvCocktail.domain.entity.Cocktails;
import com.monster.luvCocktail.domain.service.SearchService;
import com.monster.luvCocktail.domain.service.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "https://localhost:5174")
@RequestMapping("/weather")
@RestController
public class WeatherController {

    @Autowired
    private WeatherService weatherService;
    @Autowired
    private SearchService searchService;


    @GetMapping("/api/today")
    public ResponseEntity<Map<String, Object>> getRecommendCocktailsByWeather(@RequestParam double lat, @RequestParam double lon) {
        System.out.println("lat: " + lat + ", lon: " + lon);
        WeatherDTO weatherInfo = weatherService.getWeather(lat, lon).block();
        String weatherCode = weatherService.getWeatherCode(weatherInfo);

        List<Cocktails> recommendedCocktails = searchService.findCocktailsByWeatherCode(weatherCode);

        Map<String, Object> response = new HashMap<>();
        response.put("weatherInfo", weatherInfo);
        response.put("recommendedCocktails", recommendedCocktails);

        return ResponseEntity.ok(response);
    }
}