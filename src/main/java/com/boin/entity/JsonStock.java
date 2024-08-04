package com.boin.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/*
*
* 用來儲存證券交易公開api的股票資料
*/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class JsonStock {

	    public String Code;
	 	
	    public String Name;

		public String OpeningPrice;

		public String ClosingPrice;

		public String HighestPrice;

		public String LowestPrice;

		public String Change;

		public String TradeValue;
	 
	    public String TradeVolume;

	    public String Transaction;
	}

