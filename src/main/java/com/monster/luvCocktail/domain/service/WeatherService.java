package com.monster.luvCocktail.domain.service;

import com.monster.luvCocktail.domain.dto.WeatherDTO;
import com.monster.luvCocktail.domain.entity.Weather;
import com.monster.luvCocktail.domain.repository.WeatherRepository;
import org.hibernate.sql.ast.tree.from.CorrelatedTableGroup;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.converter.xml.Jaxb2RootElementHttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
public class WeatherService {

    private final WeatherRepository weatherRepository;
    @Value("${weather.api.key}")
    private String apiKey;

    public WeatherService(WeatherRepository weatherRepository) {
        this.weatherRepository = weatherRepository;
    }

    // RestTemplate 인스턴스를 생성하면서 JSON과 XML 메시지 컨버터를 추가합니다.
    private RestTemplate createRestTemplate() {
        RestTemplate restTemplate = new RestTemplate();
        List<HttpMessageConverter<?>> messageConverters = new ArrayList<>();

        // XML 메시지 컨버터 추가
//        messageConverters.add(new Jaxb2RootElementHttpMessageConverter());
        messageConverters.add(new MappingJackson2HttpMessageConverter());

        restTemplate.setMessageConverters(messageConverters);

        return restTemplate;
    }

    public WeatherDTO getWeather(double lat, double lon) {
        RestTemplate restTemplate = createRestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_TYPE, "application/json");
        headers.setAccept(Collections.singletonList(org.springframework.http.MediaType.APPLICATION_JSON));

        HttpEntity<String> entity = new HttpEntity<>(headers);

        String baseUrl = "https://apis.data.go.kr/1360000/VilageFcstInfoService_2.0/getUltraSrtFcst";

        Map<String, Integer> grid = latLonToGrid(lat, lon);
        int nx = grid.get("nx");
        int ny = grid.get("ny");

        String baseDate = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        String baseTime = "0630";

        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(baseUrl)
                .queryParam("serviceKey", apiKey)
                .queryParam("numOfRows", 1000)
                .queryParam("pageNo", 1)
                .queryParam("dataType", "JSON")
                .queryParam("base_date", baseDate)
                .queryParam("base_time", baseTime)
                .queryParam("nx", nx)
                .queryParam("ny", ny)
                .queryParam("category", "SKY","T1H"); // 원하는 카테고리 추가


        System.out.println(uriBuilder.toUriString());


        Map response = restTemplate.getForObject(uriBuilder.toUriString(), Map.class);
        System.out.println(response);
        return parseWeatherResponse(response);
    }

    private WeatherDTO parseWeatherResponse(Map<String, Object> response) {
        System.out.println("=============================");
        Map<String, Object> responseBody = (Map<String, Object>) ((Map<String, Object>) response.get("response")).get("body");
        Map<String, Object> items = (Map<String, Object>) responseBody.get("items");
        List<Map<String, String>> itemList = (List<Map<String, String>>) items.get("item");

        String weather = "Unknown";
        String temperature = "Unknown";
        StringBuilder statusBuilder = new StringBuilder();

        for (Map<String, String> item : itemList) {
            String category = item.get("category");
            String fcstValue = item.get("fcstValue");

            if ("SKY".equals(category)) {
                statusBuilder.append("SKY: ").append(fcstValue).append("; ");
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
                temperature = fcstValue + "℃";
            }

        }

        WeatherDTO weatherDTO = new WeatherDTO();
        weatherDTO.setWeather(weather);
        weatherDTO.setTemperature(temperature);
        weatherDTO.setState(statusBuilder.toString());

        return weatherDTO;
    }

    public String getWeatherCode(WeatherDTO weatherDTO) {
        Optional<Weather> weatherOpt = weatherRepository.findByWeatherNmAndWeatherSt(weatherDTO.getWeather(), weatherDTO.getState());
        return weatherOpt.map(Weather::getWeatherCd).orElse(null);
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