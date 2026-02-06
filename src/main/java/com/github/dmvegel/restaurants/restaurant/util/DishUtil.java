package com.github.dmvegel.restaurants.restaurant.util;

import com.github.dmvegel.restaurants.restaurant.model.Dish;
import com.github.dmvegel.restaurants.restaurant.to.DishTO;

import java.util.Set;
import java.util.stream.Collectors;

public class DishUtil {
    public static Set<DishTO> getListTo(Set<Dish> dishes) {
        return dishes.stream().map(dish -> new DishTO(dish.getName(), dish.getFractionPrice(), dish.getCurrency()))
                .collect(Collectors.toSet());
    }

    public static Set<Dish> getListFromTo(Set<DishTO> dishesTo) {
        return dishesTo.stream().map(dishTo -> new Dish(dishTo.getName(), dishTo.getPrice(), dishTo.getCurrencyCode()))
                .collect(Collectors.toSet());
    }
}
