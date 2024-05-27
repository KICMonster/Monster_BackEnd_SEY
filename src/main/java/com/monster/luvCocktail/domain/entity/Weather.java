package com.monster.luvCocktail.domain.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@SequenceGenerator(name = "Weather_seq_GENERATOR", sequenceName = "Weather_seq", allocationSize = 1)
public class Weather {

    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "Weather_seq_GENERATOR")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private String weatherCd;
    private String weatherCd;
    private String weatherNm;
    private String weatherSt;
}
