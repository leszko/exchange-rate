package com.summercoding.zooplus.exchange;

import com.summercoding.zooplus.model.HistoryElement;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

@RunWith(MockitoJUnitRunner.class)
public class ExchangeRateControllerTest {
    @Mock
    private ExchangeRateService exchangeRateService;

    @Mock
    private HistoryService historyService;

    @InjectMocks
    private ExchangeRateController exchangeRateController;

    private MockMvc mockMvc;

    @Mock
    private List<HistoryElement> history;

    @Before
    public void before() {
        this.mockMvc = standaloneSetup(exchangeRateController).build();

        given(historyService.retrieveHistory()).willReturn(history);
    }

    @Test
    public void Should_ShowHistoryRatesAndStandardForm_When_GetWithNoParameters() throws Exception {
        // when, then
        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("currencies", Currency.values()))
                .andExpect(model().attribute("history", history))
                .andExpect(view().name("exchangeRate"));

    }

    @Test
    public void Should_ShowLiveResultAndStoreResultInHistory_When_GetWithCurrencyButNoDate() throws Exception {
        // given
        String currency = "EUR";

        BigDecimal liveExchangeRate = new BigDecimal(4.56);
        given(exchangeRateService.live(currency)).willReturn(liveExchangeRate);

        // when, then
        mockMvc.perform(get("/")
                .param("currency", currency))
                .andExpect(status().isOk())
                .andExpect(model().attribute("result", liveExchangeRate))
                .andExpect(model().attribute("currencies", Currency.values()))
                .andExpect(model().attribute("history", history))
                .andExpect(view().name("exchangeRate"));
        verify(historyService).storeInHistory(currency, null, liveExchangeRate);
    }

    @Test
    public void Should_ShowHistoricalResultAndStoreResultInHistory_When_GetWithCurrencyAndDate() throws Exception {
        // given
        String currency = "EUR";
        String date = "2015-01-01";

        BigDecimal historicalExchangeRate = new BigDecimal(4.56);
        given(exchangeRateService.historical(currency, date)).willReturn(historicalExchangeRate);

        // when, then
        mockMvc.perform(get("/")
                .param("currency", currency)
                .param("date", date))
                .andExpect(status().isOk())
                .andExpect(model().attribute("result", historicalExchangeRate))
                .andExpect(model().attribute("currencies", Currency.values()))
                .andExpect(model().attribute("history", history))
                .andExpect(view().name("exchangeRate"));
        verify(historyService).storeInHistory(currency, date, historicalExchangeRate);
    }


}