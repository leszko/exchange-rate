package com.summercoding.zooplus.register;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * All supported countries.
 */
@Getter
@AllArgsConstructor
public enum Country {
    GERMANY("Germany"),
    POLAND("Poland"),
    FRANCE("France");

    private final String name;
}
