package ru.javaops.bootjava.restaurant.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.javaops.bootjava.common.model.NamedEntity;
import ru.javaops.bootjava.restaurant.util.CurrencyUtil;

import java.math.BigDecimal;
import java.util.Currency;

@Entity
@Table(name = "dish",
        indexes = {
                @Index(
                        columnList = "menu_id",
                        name = "idx_dish_menu_id")
        })
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Dish extends NamedEntity {
    @Column(name = "fraction_price", nullable = false)
    @NotNull
    @Min(1)
    private long fractionPrice;

    @Column(name = "currency", nullable = false)
    @NotNull
    private Currency currency;

    public Dish(String name, BigDecimal price, String currencyCode) {
        super(null, name);
        Currency currency = Currency.getInstance(currencyCode);
        this.fractionPrice = CurrencyUtil.getFractionPrice(price, currency);
        this.currency = currency;
    }
}
