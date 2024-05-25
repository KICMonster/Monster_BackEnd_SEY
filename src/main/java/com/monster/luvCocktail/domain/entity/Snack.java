package com.monster.luvCocktail.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@Getter
@Setter
@SequenceGenerator(name = "SNACK_SEQ_GENERATOR", sequenceName = "SNACK_SEQ", allocationSize = 1)
public class Snack {

	@Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "SNACE_SEQ_GENERATOR")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "SNK_ID")
	private Long id;
	
    @Column(name = "SNK_NAME")
	private String name;
    
    @Column(name = "SNK_IMAGE_L")
    private String image;
    
    @Column(name = "SNK_CATEGORY")
    private String category;
    
    @Column(name = "SNK_DESCRIPTION")
    private String description;
    
}
