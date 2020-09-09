package ua.pride.request;

import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ua.pride.service.ApiRequesterService;

import java.util.Collections;

import static ua.pride.util.JsonUtils.convertStringToJsonObject;

@Service
public class ApiRequester implements ApiRequesterService {

    @Value("${r6.token}")
    private String R6_TOKEN;
    @Value("${simple.stats.url}")
    private String SIMPLE_STATS_URL;
    @Value("${advanced.stats.url}")
    private String ADVANCED_STATS_URL;

    private final RestTemplate restTemplate;

    public ApiRequester(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public JsonElement getSiegeStats(String username) {
        String simpleStatsUrl = SIMPLE_STATS_URL + username + R6_TOKEN;
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.add("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.99 Safari/537.36");
        HttpEntity<Object> requestHeaders = new HttpEntity<>(headers);
        ResponseEntity<String> simpleStats = restTemplate.exchange(
                simpleStatsUrl,
                HttpMethod.GET,
                requestHeaders,
                String.class
        );
        boolean isFound = convertStringToJsonObject(simpleStats.getBody()).get("foundmatch").getAsBoolean();
        if (isFound) {
            String advancedStatsUrl = ADVANCED_STATS_URL +
                    convertStringToJsonObject(simpleStats.getBody())
                            .get("players")
                            .getAsJsonObject().keySet().iterator().next() + R6_TOKEN;
            simpleStats = restTemplate.exchange(
                    advancedStatsUrl,
                    HttpMethod.GET,
                    requestHeaders,
                    String.class
            );
            return convertStringToJsonObject(simpleStats.getBody());
        } else
            return JsonNull.INSTANCE;
    }


}
