package com.boin.log;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class SlackService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Value("${slack.webhook.url.debug}")
    private String slackDebugWebhookUrl;

    @Value("${slack.webhook.url.schedule}")
    private String slackScheduleWebhookUrl;

    @Autowired
    private RestTemplate restTemplate;

    public SlackService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    /*
    *
    *   Use For Debug
    *   @Author Boin Ji
    *   @Date   2024/07/19
    *
    */
    public boolean sendMessage(String message) {
        String payload = "{\"text\":\"" + message + "\"}";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> entity = new HttpEntity<>(payload, headers);
        ResponseEntity<String> response = restTemplate.postForEntity(slackDebugWebhookUrl, entity, String.class);

        if (response.getStatusCode().is2xxSuccessful()) {
            return true;
        } else {
            logger.debug("Slack fail to send error message!");
            return false;
        }
    }

    /*
     *
     *   Use For Scheduled Task
     *   @Author Boin Ji
     *   @Date   2024/07/19
     *
     */
    public boolean sendScheduledMessage(String message) {
        String payload = "{\"text\":\"" + message + "\"}";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> entity = new HttpEntity<>(payload, headers);
        ResponseEntity<String> response = restTemplate.postForEntity(slackScheduleWebhookUrl, entity, String.class);

        if (response.getStatusCode().is2xxSuccessful()) {
            return true;
        } else {
            logger.debug("Slack fail to send schedule error message!");
            return false;
        }
    }
}
