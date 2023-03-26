package com.home.textanalyzerappapi.util;

import java.util.Objects;
import java.util.function.Supplier;

public class Utils {

    public static void checkNotNull(Object o, String name) {
        Objects.requireNonNull(o, "%s should not be null".formatted(name));
    }

    public static <T> T extractSafely(Supplier<T> extractor) {
        try {
            return extractor.get();
        } catch (NullPointerException ignored) {
            return null;
        }
    }
}
