package com.boin.schedule;

import com.boin.entity.JsonStock;
import com.boin.log.SlackService;
import com.boin.repository.StockRepository;
import com.boin.service.StockService;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.List;

@EnableScheduling
@Component
public class ScheduledTasks {

    @Autowired
    private StockService stockService;
    @Autowired
    private SlackService slackService;

    /*
    *
    *   取得每日股票資訊(每日下午兩點更新)
    *   @Author Boin Ji
    *   @Date   2024/07/17
    *
    */
    @Scheduled(cron = "0 0 14 * * ?")
    public void getDailyStockData(){

        CloseableHttpClient client = HttpClients.createDefault();
        String stockURL = "https://openapi.twse.com.tw/v1/exchangeReport/STOCK_DAY_ALL";
        HttpGet get = new HttpGet(stockURL);
        List<JsonStock> stocks = null;
        try {
            CloseableHttpResponse response = client.execute(get);
            // 回應的結果Json
            // 取出回應的內容(Http header/body)
            InputStream is = response.getEntity().getContent();
            InputStreamReader reader = new InputStreamReader(is,"UTF-8");
            // 即使讀取到字串 目的要反序列化成可操作的物件
            // 如何將Json反序列化成可以操作的集合物件
            // Gson 個體物件
            Gson gson = new Gson();
            Type listType = new TypeToken<List<JsonStock>>(){}.getType();
            stocks = gson.fromJson(reader, listType);
            stockService.saveAllStock(stocks);
            slackService.sendScheduledMessage("Scheduled Daily Runtime >>>> Download Taiwanese Stock Data Success!--------");
        } catch (Exception e) {
            slackService.sendScheduledMessage("Scheduled Daily Runtime >>>> Download Taiwanese Stock Data Fail!---" + e.getMessage());
        }
    }
}
