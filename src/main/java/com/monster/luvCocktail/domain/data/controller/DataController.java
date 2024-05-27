//package com.monster.luvCocktail.domain.data.controller;
//
//
//import lombok.RequiredArgsConstructor;
//import org.json.JSONArray;
//import org.json.JSONObject;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import com.monster.luvCocktail.domain.config.ApiDefaultSetting;
//import com.monster.luvCocktail.domain.data.service.DataService;
//import com.monster.luvCocktail.domain.entity.Cocktail;
//import com.monster.luvCocktail.domain.service.CocktailService;
//
//import java.lang.reflect.InvocationTargetException;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Map;
//
//@RestController
//@RequestMapping("/api/data")
//@RequiredArgsConstructor
//public class DataController {
//
//	private final DataService dataService;
////    @Autowired
//    private final ApiDefaultSetting apiDefaultSetting;
//
//
//    @GetMapping("")
//    public String saveCocktailsList () throws InvocationTargetException, NoSuchMethodException, IllegalAccessException {
//        System.out.println("실행시작");
//        StringBuilder url = apiDefaultSetting.getUrlBuilder();
//        String response = apiDefaultSetting.getResult(url);
//        JSONArray jsonArray = apiDefaultSetting.getResultJSON(response);
//             // 칵테일 엔티티 List를 만들고
//        List<Cocktail> cocktails = new ArrayList<>();
//
//
//        // JSONArray를 반복하여 각 JSONObject를 처리
//        for (Object obj : jsonArray) {
//            JSONObject json = (JSONObject) obj;
//            Cocktail cocktail = dataService.getResult(json);
//            cocktails.add(cocktail);
//        }
//
//        dataService.saveCocktails(cocktails);
//        System.out.println("실행끝");
//        return "저장 성공!!!!!!!";
//    }
//    
////    @GetMapping("/translate-cocktails")
////    public String translateAndSaveCocktails() {
////    	dataService.translateAndSaveCocktails();
////        return "칵테일 데이터가 성공적으로 번역되어 저장되었습니다.";
////    }
//}