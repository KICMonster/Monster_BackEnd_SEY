package com.monster.luv_cocktail.domain.repository;

import com.monster.luv_cocktail.domain.entity.Weather;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WeatherRepository extends JpaRepository<Weather, String> {
    Optional<Weather> findByWeatherStatus(String weatherStatus);
}
