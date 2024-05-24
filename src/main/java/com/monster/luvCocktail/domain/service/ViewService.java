package com.monster.luvCocktail.domain.service;

import com.monster.luvCocktail.domain.entity.Cocktails;
import com.monster.luvCocktail.domain.repository.CocktailsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ViewService {
    @Autowired
    private CocktailsRepository cocktailRepository;

    @Transactional
    public void updateViews(Long id, String timestamp) {
        Cocktails cocktail = cocktailRepository.findById(id).orElseThrow(() -> new RuntimeException("Cocktail not found"));
        // 여기에 조회수 업데이트 로직을 추가합니다.
        // 예: cocktail.setViews(cocktail.getViews() + 1);
        cocktailRepository.save(cocktail);
    }
}
