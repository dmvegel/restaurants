package ru.javaops.restaurants.restaurant.util;

import ru.javaops.restaurants.restaurant.model.Restaurant;
import ru.javaops.restaurants.restaurant.to.AdminRestaurantTO;
import ru.javaops.restaurants.restaurant.to.RestaurantTO;
import ru.javaops.restaurants.restaurant.to.RestaurantWithMenuTO;

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
