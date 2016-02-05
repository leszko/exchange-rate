package com.summercoding.zooplus.api;

import com.summercoding.zooplus.dto.RegisterDto;
import com.summercoding.zooplus.constant.Country;
import com.summercoding.zooplus.service.RegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class RegisterController {

    @Autowired
    private RegisterService registerService;

    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public String showForm(RegisterDto registerDto, Model model) {
        model.addAttribute("countries", countries());
        return "registerAccount";
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public String register(@Valid RegisterDto registerDto, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("countries", countries());
            return "registerAccount";
        }

        registerService.registerUser(registerDto);

        return "redirect:/";
    }

    private static List<String> countries() {
        return Arrays.stream(Country.values())
                .map(Country::getName)
                .collect(Collectors.toList());
    }
}
