package com.github.dmvegel.restaurants.restaurant.util;

import com.github.dmvegel.restaurants.restaurant.model.Restaurant;
import com.github.dmvegel.restaurants.restaurant.to.AdminRestaurantTO;
import com.github.dmvegel.restaurants.restaurant.to.RestaurantTO;
import com.github.dmvegel.restaurants.restaurant.to.RestaurantWithMenuTO;

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
