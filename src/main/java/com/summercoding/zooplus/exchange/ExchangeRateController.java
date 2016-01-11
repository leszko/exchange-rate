package com.summercoding.zooplus.exchange;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;

@Controller
public class ExchangeRateController {
    @Autowired
    ExchangeRateService exchangeRateService;

    @Autowired
    HistoryService historyService;

    @RequestMapping(value = "/", params = {}, method = RequestMethod.GET)
    public String exchangeRate(Model model) {
        model.addAttribute("currencies", Currency.values());
        model.addAttribute("history", historyService.retrieveHistory());

        return "exchangeRate";
    }

    @RequestMapping(value = "/", params = {"currency"}, method = RequestMethod.GET)
    public String exchangeRate(@RequestParam String currency, @RequestParam(required = false) String date, Model model) {
        model.addAttribute("currencies", Currency.values());
        model.addAttribute("history", historyService.retrieveHistory());

        BigDecimal result = exchangeRate(currency, date);
        historyService.storeInHistory(currency, date, result);
        model.addAttribute("result", result);

        return "exchangeRate";
    }

    private BigDecimal exchangeRate(String currency, String date) {
        if (date == null || date.isEmpty()) {
            return exchangeRateService.live(currency);
        } else {
            return exchangeRateService.historical(currency, date);
        }
    }
}