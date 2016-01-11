package com.summercoding.zooplus.exchange;

import com.summercoding.zooplus.model.Account;
import com.summercoding.zooplus.model.HistoryElement;
import com.summercoding.zooplus.repository.AccountRepository;
import com.summercoding.zooplus.repository.HistoryElementRepository;
import com.summercoding.zooplus.security.AuthenticationNameProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
class HistoryService {

    private static final int HISTORY_SIZE = 10;

    @Autowired
    AuthenticationNameProvider authenticationNameProvider;

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    HistoryElementRepository historyElementRepository;

    public void storeInHistory(String currency, String date, BigDecimal exchangeRate) {
        Account account = currentAccount();
        List<HistoryElement> history = account.getHistory();
        removeLastHistoryElementIfSizeExceeded(history);
        addToHistory(currency, date, exchangeRate, account);
    }

    private void addToHistory(String currency, String date, BigDecimal exchangeRate, Account account) {
        HistoryElement historyElement = new HistoryElement();
        historyElement.setCurrency(currency);
        historyElement.setDate(toCurrentIfNullOrEmpty(date));
        historyElement.setExchangeRate(exchangeRate);
        historyElement.setAccount(account);

        historyElementRepository.save(historyElement);
    }

    private void removeLastHistoryElementIfSizeExceeded(List<HistoryElement> history) {
        if (history.size() >= HISTORY_SIZE) {
            HistoryElement last = history.remove(history.size() - 1);
            historyElementRepository.delete(last);
        }
    }

    private static String toCurrentIfNullOrEmpty(String date) {
        if (date == null || date.isEmpty()) {
            return "current";
        }
        return date;
    }

    public List<HistoryElement> retrieveHistory() {
        Account account = currentAccount();
        return new ArrayList<>(account.getHistory());
    }

    private Account currentAccount() {
        String accountName = authenticationNameProvider.authenticationName();
        return accountRepository.findByName(accountName);
    }
}
