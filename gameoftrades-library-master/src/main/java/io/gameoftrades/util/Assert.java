package io.gameoftrades.util;

import java.util.Objects;

/**
 * Utilily klasse om te controleren dat er geen ongeldige parameters een domein object in gaan. 
 */
public final class Assert {

    public static void notNull(Object value) {
        if (value == null) {
            throw new IllegalArgumentException("Value must not be null.");
        }
    }

    public static void positive(int value) {
        if (value <= 0) {
            throw new IllegalArgumentException("Value must be positive.");
        }
    }

    public static void notNegative(int value) {
        if (value < 0) {
            throw new IllegalArgumentException("Value must be zero or positive.");
        }
    }

    public static void notEmpty(String value) {
        if (value == null || value.isEmpty()) {
            throw new IllegalArgumentException("Value must have a value.");
        }
    }

    public static void equals(Object a, Object b) {
        if (!Objects.equals(a, b)) {
            throw new IllegalArgumentException("Expected " + a + ", not " + b);
        }
    }

}
