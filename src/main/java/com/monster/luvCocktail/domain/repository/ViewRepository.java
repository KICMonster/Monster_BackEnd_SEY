package com.monster.luvCocktail.domain.repository;

import com.monster.luvCocktail.domain.entity.View;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ViewRepository extends JpaRepository<View, Long> {
}
