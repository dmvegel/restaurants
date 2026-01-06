package ru.javaops.bootjava.restaurant.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.javaops.bootjava.common.model.NamedEntity;
import ru.javaops.bootjava.restaurant.to.DishTO;
import ru.javaops.bootjava.restaurant.util.CurrencyUtil;

import java.util.Currency;

@Entity
@Table(name = "dish")
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "menu_id")
    @NotNull
    @JsonBackReference
    private Menu menu;

    public Dish(DishTO dishTo) {
        super(null, dishTo.getName());
        Currency currency = Currency.getInstance(dishTo.getCurrency());
        this.fractionPrice = CurrencyUtil.getFractionPrice(dishTo.getPrice(), currency);
        this.currency = currency;
    }
}
