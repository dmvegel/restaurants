package com.github.dmvegel.restaurants.restaurant.to;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Value;

import java.time.LocalDate;
import java.util.Set;
import java.util.stream.Collectors;

@Value
public class MenuTO {
    @NotNull
    LocalDate date;

    @Valid
    @NotEmpty
    Set<DishTO> dishes;

    @JsonCreator
    public MenuTO(@JsonProperty("date") LocalDate date,
                  @JsonProperty("dishes") Set<DishTO> dishes) {
        this.date = date;
        this.dishes = dishes;
    }

    @Override
    public String toString() {
        return "MenuTO{" +
                "date=" + date +
                ", dishes=\n" +
                dishes.stream()
                        .map(DishTO::toString)
                        .collect(Collectors.joining("\n")) +
                "\n}";
    }
}
