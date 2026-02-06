package com.github.dmvegel.restaurants.restaurant.to;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import lombok.Value;

import java.util.Set;

@Value
public class MenuTO {
    @Valid
    @NotEmpty
    Set<DishTO> dishes;

    @JsonCreator
    public MenuTO(@JsonProperty("dishes") Set<DishTO> dishes) {
        this.dishes = dishes;
    }
}
