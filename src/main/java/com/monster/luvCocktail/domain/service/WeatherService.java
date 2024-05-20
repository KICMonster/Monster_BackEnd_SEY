package com.monster.luvCocktail.domain.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Map;

@Service
public class WeatherService {

    @Value("${weather.api.key}")
    private String apiKey;

    public Map<String, Object> getWeather(double lat, double lon) {
        RestTemplate restTemplate = new RestTemplate();
        String baseUrl = "https://apis.data.go.kr/1360000/VilageFcstInfoService_2.0/getUltraSrtFcst";

        // Grid 좌표 변환
        Map<String, Integer> grid = latLonToGrid(lat, lon);
        int nx = grid.get("nx");
        int ny = grid.get("ny");

        String baseDate = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        String baseTime = "0630";

        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(baseUrl)
                .queryParam("serviceKey", apiKey)
                .queryParam("numOfRows", 10)
                .queryParam("pageNo", 1)
                .queryParam("dataType", "JSON")
                .queryParam("base_date", baseDate)
                .queryParam("base_time", baseTime)
                .queryParam("nx", nx)
                .queryParam("ny", ny);

        Map<String, Object> response = restTemplate.getForObject(uriBuilder.toUriString(), Map.class);
        return response;
    }

    private Map<String, Integer> latLonToGrid(double lat, double lon) {
        // 좌표 변환 로직 (리액트 코드의 latLonToGrid 메서드와 동일)
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

