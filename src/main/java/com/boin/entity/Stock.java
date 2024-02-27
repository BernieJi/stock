package com.boin.entity;

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

import lombok.Data;


@Entity
public class Stock {
	
		@Id
	    public String Code;
	 	
	    public String Name;
	 
	    public String TradeVolume;
	   
	    public String TradeValue;
	    
	    public String OpeningPrice;
	    
	    public String HighestPrice;
	    
	    public String LowestPrice;
	    
	    public String ClosingPrice;
	    
	    @Column(name="stock_change")
	    public String Change;
	    
	    @Column(name="stock_transaction")
	    public String Transaction;
	    
	    @ManyToMany(mappedBy = "stocks")
	    public Set<WatchList> watchList = new HashSet<>();

		public String getCode() {
			return Code;
		}

		public void setCode(String code) {
			Code = code;
		}

		public String getName() {
			return Name;
		}

		public void setName(String name) {
			Name = name;
		}

		public String getTradeVolume() {
			return TradeVolume;
		}

		public void setTradeVolume(String tradeVolume) {
			TradeVolume = tradeVolume;
		}

		public String getTradeValue() {
			return TradeValue;
		}

		public void setTradeValue(String tradeValue) {
			TradeValue = tradeValue;
		}

		public String getOpeningPrice() {
			return OpeningPrice;
		}

		public void setOpeningPrice(String openingPrice) {
			OpeningPrice = openingPrice;
		}

		public String getHighestPrice() {
			return HighestPrice;
		}

		public void setHighestPrice(String highestPrice) {
			HighestPrice = highestPrice;
		}

		public String getLowestPrice() {
			return LowestPrice;
		}

		public void setLowestPrice(String lowestPrice) {
			LowestPrice = lowestPrice;
		}

		public String getClosingPrice() {
			return ClosingPrice;
		}

		public void setClosingPrice(String closingPrice) {
			ClosingPrice = closingPrice;
		}

		public String getChange() {
			return Change;
		}

		public void setChange(String change) {
			Change = change;
		}

		public String getTransaction() {
			return Transaction;
		}

		public void setTransaction(String transaction) {
			Transaction = transaction;
		}

		public Set<WatchList> getWatchList() {
			return watchList;
		}

		public void setWatchList(Set<WatchList> watchList) {
			this.watchList = watchList;
		}

		@Override
		public String toString() {
			return "Stock [Code=" + Code + ", Name=" + Name + ", TradeVolume=" + TradeVolume + ", TradeValue="
					+ TradeValue + ", OpeningPrice=" + OpeningPrice + ", HighestPrice=" + HighestPrice
					+ ", LowestPrice=" + LowestPrice + ", ClosingPrice=" + ClosingPrice + ", Change=" + Change
					+ ", Transaction=" + Transaction + ", watchList=" + watchList + "]";
		}
	    
	    
	    
	    
	}

