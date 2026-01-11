package ru.javaops.restaurants.restaurant.util;

import ru.javaops.restaurants.restaurant.model.Menu;
import ru.javaops.restaurants.restaurant.model.Restaurant;
import ru.javaops.restaurants.restaurant.to.MenuTO;

import java.util.List;

public class MenuUtil {
    public static List<MenuTO> getListTo(List<Menu> menus) {
        return menus.stream().map(menu -> new MenuTO(menu.getId(), menu.getDate(), DishUtil.getListTo(menu.getDishes())))
                .toList();
    }

    public static MenuTO getTo(Menu menu) {
        return new MenuTO(menu.getId(), menu.getDate(), DishUtil.getListTo(menu.getDishes()));
    }

    public static Menu getFromTo(MenuTO menuTo, Restaurant restaurant) {
        return new Menu(menuTo.getId(), menuTo.getDate(), DishUtil.getListFromTo(menuTo.getDishes()), restaurant);
    }
}
