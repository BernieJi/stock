package com.boin.entity;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class Stock {

	    public String code;
	 	
	    public String name;

		public String openingPrice;

		public String closingPrice;

		public String highestPrice;

		public String lowestPrice;

		public String change;

		public String tradeValue;
	 
	    public String tradeVolume;

	    public String transaction;
	}

