package com.monster.luv_cocktail.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import java.time.ZonedDateTime;

import lombok.Data;
import net.minidev.json.annotate.JsonIgnore;

@Data
@Entity
@Table(
        name = "viewLog"
)
@SequenceGenerator(
        name = "view_seq",
        sequenceName = "view_seq",
        allocationSize = 1
)
public class ViewLog {
    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "View_seq"
    )
    private long viewCd;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(
            name = "VIEW_DATE",
            nullable = false
    )
    private ZonedDateTime viewDate;
    @ManyToOne
    @JoinColumn(
            name = "cocktail_id"
    )
    @JsonIgnore
    private Cocktail cocktail;
}