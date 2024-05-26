package com.monster.luv_cocktail.domain.dto;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.ZonedDateTime;

@RequiredArgsConstructor
@Data
public class ViewDTO {
    private long viewCd;
    private ZonedDateTime viewDate;
    private String name;
    private Long id;

    // Getters and setters
}
