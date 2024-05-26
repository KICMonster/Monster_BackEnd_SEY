package com.monster.luv_cocktail.domain.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "RECORD")
public class Record {

    @Id
    @Column(name = "QUESTION_CD", nullable = false, length = 255)
    private String questionCd;

    @Column(name = "QUESTION", nullable = false, length = 255)
    private String question;

    @Column(name = "RECORD_RES", nullable = false, length = 255)
    private String recordRes;

    @OneToMany(mappedBy = "record")
    private List<Member> members;
}
