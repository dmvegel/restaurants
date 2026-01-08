package ru.javaops.bootjava.restaurant.util;

import ru.javaops.bootjava.restaurant.model.Dish;
import ru.javaops.bootjava.restaurant.model.Menu;
import ru.javaops.bootjava.restaurant.to.DishTO;

import java.util.Set;
import java.util.stream.Collectors;

public class DishUtil {
    public static Set<DishTO> getListTo(Set<Dish> dishes) {
        return dishes.stream().map(dish -> new DishTO(dish.getName(), dish.getFractionPrice(), dish.getCurrency()))
                .collect(Collectors.toSet());
    }

    public static Set<Dish> getListFromTo(Set<DishTO> dishesTo, Menu menu) {
        return dishesTo.stream().map(dishTo -> new Dish(dishTo.getName(), dishTo.getPrice(), dishTo.getCurrencyCode(), menu))
                .collect(Collectors.toSet());
    }

    public static Set<Dish> getListFromTo(Set<DishTO> dishesTo) {
        return dishesTo.stream().map(dishTo -> new Dish(dishTo.getName(), dishTo.getPrice(), dishTo.getCurrencyCode()))
                .collect(Collectors.toSet());
    }
}
