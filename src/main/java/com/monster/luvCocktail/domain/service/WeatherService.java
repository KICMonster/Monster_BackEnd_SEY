package com.monster.luvCocktail.domain.service;

import com.monster.luvCocktail.domain.dto.WeatherDTO;
import com.monster.luvCocktail.domain.entity.Weather;
import com.monster.luvCocktail.domain.repository.WeatherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
public class WeatherService {

    private final WeatherRepository weatherRepository;
    private final WebClient webClient;

    @Value("${weather.api.key}")
    private String apiKey;

    @Autowired
    public WeatherService(WeatherRepository weatherRepository, WebClient webClient) {
        this.weatherRepository = weatherRepository;
        this.webClient = webClient;
    }

    public Mono<WeatherDTO> getWeather(double lat, double lon) {
        Map<String, Integer> grid = latLonToGrid(lat, lon);
        int nx = grid.get("nx");
        int ny = grid.get("ny");

        String baseDate = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        String baseTime = "0630";

        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/1360000/VilageFcstInfoService_2.0/getUltraSrtFcst")
                        .queryParam("serviceKey", apiKey)
                        .queryParam("numOfRows", 1000)
                        .queryParam("pageNo", 1)
                        .queryParam("dataType", "JSON")
                        .queryParam("base_date", baseDate)
                        .queryParam("base_time", baseTime)
                        .queryParam("nx", nx)
                        .queryParam("ny", ny)
                        .queryParam("category", "SKY,T1H")
                        .build())
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(Map.class)
                .map(this::parseWeatherResponse);
    }

    private WeatherDTO parseWeatherResponse(Map<String, Object> response) {
        Map<String, Object> responseBody = (Map<String, Object>) ((Map<String, Object>) response.get("response")).get("body");
        Map<String, Object> items = (Map<String, Object>) responseBody.get("items");
        List<Map<String, String>> itemList = (List<Map<String, String>>) items.get("item");

        String weather = "Unknown";
        String temperature = "Unknown";
        StringBuilder statusBuilder = new StringBuilder();
        StringBuilder categoryBuilder = new StringBuilder(); // 카테고리를 저장할 StringBuilder 추가

        for (Map<String, String> item : itemList) {
            String category = item.get("category");
            String fcstValue = item.get("fcstValue");

            if ("SKY".equals(category)) {
                statusBuilder.append("SKY: ").append(fcstValue).append("; ");
                categoryBuilder.append("SKY").append("; "); // 카테고리 추가
                weather = switch (fcstValue) {
                    case "1" -> "맑은 상태로";
                    case "2" -> "비가 오는 상태로";
                    case "3" -> "구름이 많은 상태로";
                    case "4" -> "흐림";
                    default -> weather;
                };
            }

            if ("T3H".equals(category) || "T1H".equals(category)) {
                statusBuilder.append(category).append(": ").append(fcstValue).append("; ");
                categoryBuilder.append(category).append("; "); // 카테고리 추가
                temperature = fcstValue + "℃";
            }
        }

        WeatherDTO weatherDTO = new WeatherDTO();
        weatherDTO.setWeather(weather);
        weatherDTO.setTemperature(temperature);
        weatherDTO.setCategory(categoryBuilder.toString()); // 카테고리 설정

        return weatherDTO;
    }

    public String getWeatherCode(WeatherDTO weatherDTO) {
        Optional<Weather> weatherOpt = weatherRepository.findByWeatherSt(weatherDTO.getWeather());
        return weatherOpt.map(weather -> String.valueOf(weather.getWeatherCd())).orElse(null); // Long 타입에서 String 타입으로 변환
    }

    private Map<String, Integer> latLonToGrid(double lat, double lon) {
        final double RE = 6371.00877;
        final double GRID = 5.0;
        final double SLAT1 = 30.0;
        final double SLAT2 = 60.0;
        final double OLON = 126.0;
        final double OLAT = 38.0;
        final int XO = 43;
        final int YO = 136;

        double DEGRAD = Math.PI / 180.0;
        double re = RE / GRID;
        double slat1 = SLAT1 * DEGRAD;
        double slat2 = SLAT2 * DEGRAD;
        double olon = OLON * DEGRAD;
        double olat = OLAT * DEGRAD;

        double sn = Math.tan(Math.PI * 0.25 + slat2 * 0.5) / Math.tan(Math.PI * 0.25 + slat1 * 0.5);
        sn = Math.log(Math.cos(slat1) / Math.cos(slat2)) / Math.log(sn);
        double sf = Math.tan(Math.PI * 0.25 + slat1 * 0.5);
        sf = (sf * sn * Math.cos(slat1)) / sn;
        double ro = Math.tan(Math.PI * 0.25 + olat * 0.5);
        ro = (re * sf) / (ro * sn);

        double ra = Math.tan(Math.PI * 0.25 + lat * DEGRAD * 0.5);
        ra = (re * sf) / (ra * sn);
        double theta = lon * DEGRAD - olon;
        if (theta > Math.PI) theta -= 2.0 * Math.PI;
        if (theta < -Math.PI) theta += 2.0 * Math.PI;
        theta *= sn;

        int nx = (int) (ra * Math.sin(theta) + XO + 0.5);
        int ny = (int) (ro - ra * Math.cos(theta) + YO + 0.5);

        return Map.of("nx", nx, "ny", ny);
    }
}