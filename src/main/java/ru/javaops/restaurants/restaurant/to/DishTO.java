package ru.javaops.restaurants.restaurant.to;

import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.Value;
import ru.javaops.restaurants.common.validation.NoHtml;
import ru.javaops.restaurants.restaurant.util.CurrencyUtil;

import java.math.BigDecimal;
import java.util.Currency;

@Value
public class DishTO {
    @NotBlank
    @Size(min = 1, max = 64)
    @NoHtml
    String name;

    @NotNull
    @Positive
    BigDecimal price;

    @NotBlank
    @Schema(
            description = "currency code",
            example = "RUB",
            minLength = CurrencyUtil.CODE_LENGTH,
            maxLength = CurrencyUtil.CODE_LENGTH,
            pattern = CurrencyUtil.CODE_PATTERN
    )
    @Size(min = CurrencyUtil.CODE_LENGTH, max = CurrencyUtil.CODE_LENGTH)
    @Pattern(regexp = CurrencyUtil.CODE_PATTERN)
    @NoHtml
    String currencyCode;

    @JsonCreator
    public DishTO(String name, BigDecimal price, String currencyCode) {
        this.name = name;
        this.price = price;
        this.currencyCode = currencyCode;
    }

    public DishTO(String name, long fractionPrice, Currency currency) {
        this.name = name;
        this.currencyCode = currency.getCurrencyCode();
        this.price = CurrencyUtil.getPrice(fractionPrice, currency);
    }
}
