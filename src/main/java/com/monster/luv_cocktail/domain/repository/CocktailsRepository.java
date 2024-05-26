package com.monster.luv_cocktail.domain.repository;

import java.time.LocalDateTime;
import java.util.List;

import com.monster.luv_cocktail.domain.entity.Cocktail;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CocktailsRepository extends JpaRepository<Cocktail, Long> {
    List<Cocktail> findByTasteIn(List<String> tasteIds);

    List<Cocktail> findByWeather(String weatherCode);

    @EntityGraph(
            attributePaths = {"views"}
    )
    List<Cocktail> findAll();

    @Query("SELECT c FROM Cocktail c JOIN c.views v WHERE v.viewDate BETWEEN :startDateTime AND :endDateTime")
    List<Cocktail> findByViewLogBetween(@Param("startDateTime") LocalDateTime startDateTime, @Param("endDateTime") LocalDateTime endDateTime);
}

