package com.github.dmvegel.restaurants.restaurant.util;

import com.github.dmvegel.restaurants.restaurant.model.Menu;
import com.github.dmvegel.restaurants.restaurant.model.Restaurant;
import com.github.dmvegel.restaurants.restaurant.to.MenuTO;

import java.time.LocalDate;
import java.util.List;

public class MenuUtil {
    public static List<MenuTO> getListTo(List<Menu> menus) {
        return menus.stream().map(menu -> new MenuTO(menu.getDate(), DishUtil.getListTo(menu.getDishes())))
                .toList();
    }

    public static MenuTO getTo(Menu menu) {
        return new MenuTO(menu.getDate(), DishUtil.getListTo(menu.getDishes()));
    }

    public static Menu getFromTo(MenuTO menuTo, Restaurant restaurant, LocalDate date) {
        return new Menu(null, date, DishUtil.getListFromTo(menuTo.getDishes()), restaurant);
    }
}
