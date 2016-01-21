package com.summercoding.zooplus.register;

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
    public String showForm(RegisterForm registerForm, Model model) {
        model.addAttribute("countries", countries());
        return "register";
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public String registerFranek(@Valid RegisterForm registerForm, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("countries", countries());
            return "register";
        }

        registerService.registerUser(registerForm);

        return "redirect:/";
    }

    private static List<String> countries() {
        return Arrays.stream(Country.values())
                .map(Country::getName)
                .collect(Collectors.toList());
    }
}
