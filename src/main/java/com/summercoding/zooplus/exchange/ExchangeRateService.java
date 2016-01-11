package com.summercoding.zooplus.exchange;

import com.google.gson.JsonParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.math.BigDecimal;

@Service
class ExchangeRateService {

    private static final String URL_LIVE = "http://apilayer.net/api/live";
    private static final String URL_HISTORICAL = "http://apilayer.net/api/historical";

    @Value("${apilayer.key}")
    private String key;

    @Autowired
    private RestTemplate restTemplate;

    public BigDecimal live(String currency) {
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(URL_LIVE)
                .queryParam("access_key", key)
                .queryParam("currencies", currency);
        return requestExchangeRate(builder);
    }

    public BigDecimal historical(String currency, String date) {
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(URL_HISTORICAL)
                .queryParam("access_key", key)
                .queryParam("currencies", currency)
                .queryParam("date", date);
        return requestExchangeRate(builder);
    }

    private BigDecimal requestExchangeRate(UriComponentsBuilder builder) {
        String result = restTemplate.getForObject(builder.toUriString(), String.class);

        return new JsonParser()
                .parse(result).getAsJsonObject()
                .get("quotes").getAsJsonObject()
                .entrySet().iterator().next()
                .getValue().getAsBigDecimal();
    }
}
