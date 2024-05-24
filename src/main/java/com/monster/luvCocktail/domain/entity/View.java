package com.monster.luvCocktail.domain.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "viewLog") // 테이블 이름 지정
@SequenceGenerator(name = "view_seq", sequenceName = "view_seq", allocationSize = 1)
public class View {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "View_seq")
    private long viewCd;
    private String viewLog;

    @ManyToOne
    @JoinColumn(name = "cocktail_id")
    private Cocktails cocktail;
}
