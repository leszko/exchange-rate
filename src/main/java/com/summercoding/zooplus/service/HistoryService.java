package com.summercoding.zooplus.service;

import com.summercoding.zooplus.model.User;
import com.summercoding.zooplus.model.ExchangeRateRequest;
import com.summercoding.zooplus.repository.UserRepository;
import com.summercoding.zooplus.repository.HistoryElementRepository;
import com.summercoding.zooplus.security.AuthenticationNameProvider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Stores the history of queries per user.
 */
@Slf4j
@Service
public class HistoryService {
    @Value("${history.size}")
    private int historySize;

    @Autowired
    AuthenticationNameProvider authenticationNameProvider;

    @Autowired
    UserRepository userRepository;

    @Autowired
    HistoryElementRepository historyElementRepository;

    public void storeInHistory(String currency, String date, BigDecimal exchangeRate) {
        log.info("Storing query in the history with currency: {}, date: {}, exchange rate: {}", currency, date, exchangeRate);

        User user = currentAccount();

        List<ExchangeRateRequest> history = user.getHistory();
        removeLastHistoryElementIfSizeExceeded(history);
        addToHistory(currency, date, exchangeRate, user);
    }

    private void removeLastHistoryElementIfSizeExceeded(List<ExchangeRateRequest> history) {
        if (history.size() >= historySize) {
            ExchangeRateRequest last = history.remove(history.size() - 1);

            log.info("History size exceeds maximum size, removing the oldest query: {}", last);

            historyElementRepository.delete(last);
        }
    }

    private void addToHistory(String currency, String date, BigDecimal exchangeRate, User user) {
        ExchangeRateRequest historyElement = new ExchangeRateRequest();
        historyElement.setCurrency(currency);
        historyElement.setDate(toCurrentIfNullOrEmpty(date));
        historyElement.setExchangeRate(exchangeRate);
        historyElement.setUser(user);

        historyElementRepository.save(historyElement);
    }

    private static String toCurrentIfNullOrEmpty(String date) {
        if (date == null || date.isEmpty()) {
            return "current";
        }
        return date;
    }

    public List<ExchangeRateRequest> retrieveHistory() {
        User user = currentAccount();
        return new ArrayList<>(user.getHistory());
    }

    private User currentAccount() {
        String accountName = authenticationNameProvider.authenticationName();
        return userRepository.findByName(accountName);
    }
}
