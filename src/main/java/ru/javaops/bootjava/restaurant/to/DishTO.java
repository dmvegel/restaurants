package ru.javaops.bootjava.restaurant.to;

import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.Value;
import ru.javaops.bootjava.restaurant.model.Dish;
import ru.javaops.bootjava.restaurant.util.CurrencyUtil;

import java.math.BigDecimal;

@Value
public class DishTO {
    @NotBlank
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
    String currency;

    @JsonCreator
    public DishTO(String name, BigDecimal price, String currency) {
        this.name = name;
        this.price = price;
        this.currency = currency;
    }

    public DishTO(Dish dish) {
        this.name = dish.getName();
        this.currency = dish.getCurrency().getCurrencyCode();
        this.price = CurrencyUtil.getPrice(dish.getFractionPrice(), dish.getCurrency());
    }
}
