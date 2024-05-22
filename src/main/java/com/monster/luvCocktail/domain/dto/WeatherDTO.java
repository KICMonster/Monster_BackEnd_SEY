package com.monster.luvCocktail.domain.dto;

import lombok.Data;


@Data
public class WeatherDTO {
    private String weather;
    private String temperature;
    private String state;
    private double lat;
    private double lon;

    public WeatherDTO() {}

    public WeatherDTO(String weather, String temperature, String state) {
        this.weather = weather;
        this.temperature = temperature;
        this.state = state;
    }

}
