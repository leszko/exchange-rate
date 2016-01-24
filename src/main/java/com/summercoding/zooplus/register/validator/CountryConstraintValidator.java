package com.summercoding.zooplus.register.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Validator which checks if the country is supported.
 */
public class CountryConstraintValidator implements ConstraintValidator<Country, String> {
    private Set<String> countryNames = initializeCountrySet();


    @Override
    public void initialize(Country country) {
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return countryNames.contains(value);
    }

    private static Set<String> initializeCountrySet() {
        return Arrays.stream(com.summercoding.zooplus.register.Country.values())
                .map(country -> country.getName())
                .collect(Collectors.toSet());
    }
}
