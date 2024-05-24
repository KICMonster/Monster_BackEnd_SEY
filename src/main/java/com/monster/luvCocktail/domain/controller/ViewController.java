package com.monster.luvCocktail.domain.controller;

import com.monster.luvCocktail.domain.entity.Cocktails;
import com.monster.luvCocktail.domain.entity.View;
import com.monster.luvCocktail.domain.repository.CocktailsRepository;
import com.monster.luvCocktail.domain.repository.ViewRepository;
import com.monster.luvCocktail.domain.service.ViewService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 컨트롤러 클래스는 HTTP 요청을 처리하고 응답을 반환합니다.
 */
@CrossOrigin(origins = "https://localhost:5174")
@RequestMapping("/view")
@RestController
public class ViewController {
    private final ViewService viewService;
    private final CocktailsRepository cocktailsRepository;
    private final ViewRepository viewRepository;
    private final ExecutorService executorService = Executors.newFixedThreadPool(10); // 적절한 스레드 풀 설정(다중 스레드 처리)

    public ViewController(ViewService viewService, CocktailsRepository cocktailsRepository, ViewRepository viewRepository) {
        this.viewService = viewService;
        this.cocktailsRepository = cocktailsRepository;
        this.viewRepository = viewRepository;
    }

    /**
     * 칵테일 조회수를 업데이트합니다.
     * @param id 칵테일 식별자
     * @param request 요청 매개변수
     * @return 요청 처리 결과에 따른 응답
     */
    @PutMapping("/api/cocktails/{id}")
    public ResponseEntity<Void> updateCocktailViews(@PathVariable Long id, @RequestBody Map<String, String> request) {
        String timestamp = request.get("timestamp");

        // 칵테일 조회
        Cocktails cocktail = cocktailsRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cocktail not found"));

        // 비동기로 조회 로그 생성 처리
        executorService.submit(() -> {
            View view = new View();
            view.setCocktail(cocktail);
            view.setViewLog(timestamp);
            viewRepository.save(view);
        });

        return ResponseEntity.ok().build();
    }
}