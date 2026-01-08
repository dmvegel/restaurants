package ru.javaops.bootjava.restaurant.util;

import ru.javaops.bootjava.restaurant.model.Restaurant;
import ru.javaops.bootjava.restaurant.to.AdminRestaurantTO;
import ru.javaops.bootjava.restaurant.to.RestaurantTO;
import ru.javaops.bootjava.restaurant.to.RestaurantWithMenuTO;

import java.util.HashSet;

public class RestaurantUtil {
    public static RestaurantWithMenuTO getWithMenuTo(Restaurant restaurant) {
        return new RestaurantWithMenuTO(
                restaurant.getId(),
                restaurant.getName(),
                new HashSet<>(MenuUtil.getListTo(restaurant.getMenus().stream().toList())));
    }

    public static RestaurantTO getTo(Restaurant restaurant) {
        return new RestaurantTO(
                restaurant.getId(),
                restaurant.getName()
        );
    }

    public static AdminRestaurantTO getAdminTo(Restaurant restaurant) {
        return new AdminRestaurantTO(
                restaurant.getId(),
                restaurant.getName(),
                restaurant.isEnabled()
        );
    }

    public static AdminRestaurantTO getAdminTo(RestaurantTO restaurantTo, boolean enabled) {
        return new AdminRestaurantTO(
                restaurantTo.getId(),
                restaurantTo.getName(),
                enabled
        );
    }
}
