package com.monster.luvCocktail.domain.repository;

import com.monster.luvCocktail.domain.entity.Weather;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface WeatherRepository extends JpaRepository<Weather, Long> {
    Optional<Weather> findByWeatherNmAndWeatherSt(String weatherNm, String weatherSt);
}