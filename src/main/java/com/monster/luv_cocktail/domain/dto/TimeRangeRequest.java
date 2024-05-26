package com.monster.luv_cocktail.domain.dto;

import java.time.ZonedDateTime;

public class TimeRangeRequest {
    private ZonedDateTime start;
    private ZonedDateTime end;

    // getters and setters
    public ZonedDateTime getStart() {
        return start;
    }

    public void setStart(ZonedDateTime start) {
        this.start = start;
    }

    public ZonedDateTime getEnd() {
        return end;
    }

    public void setEnd(ZonedDateTime end) {
        this.end = end;
    }
}
