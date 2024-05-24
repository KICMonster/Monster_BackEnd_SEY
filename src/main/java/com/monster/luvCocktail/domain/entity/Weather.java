package com.monster.luvCocktail.domain.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@SequenceGenerator(name = "Weather_seq", sequenceName = "Weather_seq", allocationSize = 1)
public class Weather {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "Weather_seq")
    private String weatherCd;
    private String weatherNm;
    private String weatherSt;
}
