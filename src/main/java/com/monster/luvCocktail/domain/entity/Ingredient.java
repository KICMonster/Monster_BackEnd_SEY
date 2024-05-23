package com.monster.luvCocktail.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class Ingredient {

	@Id
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
