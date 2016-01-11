package com.summercoding.zooplus.exchange;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;

import static com.google.common.truth.Truth.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.anyString;

@RunWith(MockitoJUnitRunner.class)
public class ExchangeRateServiceTest {
    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private ExchangeRateService service;

    @Test
    public void Should_ReturnLiveExchangeRatio_When_CorrectResponseFromWebService() {
        // given
        given(restTemplate.getForObject(anyString(), anyObject())).willReturn("{\"quotes\" : {\"USDEUR\":1.56}}");

        // when
        BigDecimal result = service.live("EUR");

        // then
        assertThat(result).isEqualTo(new BigDecimal("1.56"));
    }

    @Test(expected = Exception.class)
    public void Should_ThrowException_When_NotParsableLiveResponseFromWebService() {
        // given
        given(restTemplate.getForObject(anyString(), anyObject())).willReturn("NOT_PARSABLE");

        // when
        service.live("EUR");
    }

    @Test
    public void Should_ReturnHistoricalExchangeRatio_When_CorrectResponseFromWebService() {
        // given
        given(restTemplate.getForObject(anyString(), anyObject())).willReturn("{\"quotes\" : {\"USDEUR\":1.56}}");

        // when
        BigDecimal result = service.historical("EUR", "2015-01-01");

        // then
        assertThat(result).isEqualTo(new BigDecimal("1.56"));
    }

    @Test(expected = Exception.class)
    public void Should_ThrowException_When_NotParsableHistoricalResponseFromWebService() {
        // given
        given(restTemplate.getForObject(anyString(), anyObject())).willReturn("NOT_PARSABLE");

        // when
        service.historical("EUR", "2015-01-01");
    }


}