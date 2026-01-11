package ru.javaops.bootjava.restaurant.util;

import java.math.BigDecimal;
import java.util.Currency;

public class CurrencyUtil {
    public static final int CODE_LENGTH = 3;
    public static final String CODE_PATTERN = "^[A-Z]{3}$";

    public static long getFractionPrice(BigDecimal price, Currency currency) {
        int fractionDigits = currency.getDefaultFractionDigits();
        return price.movePointRight(fractionDigits).longValueExact();
    }

    public static BigDecimal getPrice(long fractionPrice, Currency currency) {
        int fractionDigits = currency.getDefaultFractionDigits();
        return BigDecimal.valueOf(fractionPrice)
                .movePointLeft(fractionDigits);
    }
}
