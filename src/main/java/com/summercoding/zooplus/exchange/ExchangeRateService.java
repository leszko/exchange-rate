package com.summercoding.zooplus.exchange;

import com.google.gson.JsonParser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.math.BigDecimal;

@Service
@Slf4j
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

    @Cacheable("historicalExchangeRates")
    public BigDecimal historical(String currency, String date) {
        log.info("Sending request to get historical exchange rates for currency: {} and date: {}.", currency, date);

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
