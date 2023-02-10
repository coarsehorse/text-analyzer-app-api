package com.home.textanalyzerappapi.util;

import java.util.Objects;

public class Utils {

    public static void checkNotNull(Object o, String name) {
        Objects.requireNonNull(o, "%s should not be null".formatted(name));
    }
}
