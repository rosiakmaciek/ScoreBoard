package com.scoreboard;

import java.util.Arrays;
import java.util.Locale;
import java.util.Set;
import java.util.stream.Collectors;

public class CountryUtils {

    public static Set<String> countries;

    static {
        countries = Arrays.stream(Locale.getISOCountries())
                .map(code -> new Locale("", code))
                .map(locale -> locale.getDisplayCountry(Locale.ENGLISH))
                .collect(Collectors.toSet());
    }
}