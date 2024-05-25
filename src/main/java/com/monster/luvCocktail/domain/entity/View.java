package com.monster.luvCocktail.domain.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "viewLog") // 테이블 이름 지정
@SequenceGenerator(name = "View_seq_GENERATOR", sequenceName = "view_seq", allocationSize = 1)
public class View {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "View_seq_GENERATOR")
    private Long viewCd;
    private String viewLog;

    @ManyToOne
    @JoinColumn(name = "cocktail_id")
    private Cocktail cocktail;
}
