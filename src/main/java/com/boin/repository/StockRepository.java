//package com.boin.repository;
//
//import org.apache.http.client.methods.CloseableHttpResponse;
//import org.apache.http.client.methods.HttpGet;
//import org.apache.http.impl.client.CloseableHttpClient;
//import org.apache.http.impl.client.HttpClients;
//import org.springframework.stereotype.Repository;
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.jpa.repository.Query;
//
//import com.google.gson.Gson;
//import com.google.gson.reflect.TypeToken;
//import com.boin.entity.Stock;
//
//@Repository
//public interface StockRepository extends JpaRepository<Stock, String> {
//
//	@Query(value = "SELECT * from stock where stock.code = ?1", nativeQuery = true)
//	Stock getByCode(String code);
//
//}
