package ru.javaops.restaurants.restaurant.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.javaops.restaurants.common.error.NotFoundException;
import ru.javaops.restaurants.common.service.AbstractServiceTest;
import ru.javaops.restaurants.restaurant.to.MenuTO;

import static ru.javaops.restaurants.restaurant.MenuTestData.*;
import static ru.javaops.restaurants.restaurant.RestaurantTestData.DISABLED_RESTAURANT_ID;
import static ru.javaops.restaurants.restaurant.RestaurantTestData.RESTAURANT_1_ID;

class MenuServiceTest extends AbstractServiceTest {
    @Autowired
    MenuService menuService;

    @Test
    void create() {
        MenuTO created = menuService.create(getNew(), RESTAURANT_1_ID);
        MenuTO menuTo = getNew();
        menuTo.setId(created.getId());
        MENU_TO_MATCHER.assertMatch(menuService.get(RESTAURANT_1_ID, NEW_MENU_DATE), menuTo);
    }

    @Test
    void updateMenu() {
        menuService.update(getUpdated(), RESTAURANT_1_ID);
        MenuTO menuTo = getUpdated();
        MENU_TO_MATCHER.assertMatch(menuService.get(RESTAURANT_1_ID, MENU_DATE), menuTo);
    }

    @Test
    void createDisabledRestaurantMenu() {
        MenuTO created = menuService.create(getNew(), DISABLED_RESTAURANT_ID);
        MenuTO menuTo = getNew();
        menuTo.setId(created.getId());
        MENU_TO_MATCHER.assertMatch(menuService.get(DISABLED_RESTAURANT_ID, NEW_MENU_DATE), menuTo);
    }

    @Test
    void updateDisabledRestaurantMenu() {
        MenuTO menuTo = getUpdated();
        menuTo.setId(MENU_4_ID);
        menuService.update(menuTo, DISABLED_RESTAURANT_ID);
        MENU_TO_MATCHER.assertMatch(menuService.get(DISABLED_RESTAURANT_ID, MENU_DATE), menuTo);
    }

    @Test
    void get() {
        MENU_TO_MATCHER.assertMatch(menuService.get(RESTAURANT_1_ID, MENU_DATE), menu_1);
    }

    @Test
    void getEnabled() {
        MENU_TO_MATCHER.assertMatch(menuService.getEnabled(MENU_1_ID, MENU_DATE), menu_1);
    }

    @Test
    void getDisabled() {
        validateRootCause(NotFoundException.class, () -> menuService.getEnabled(MENU_4_ID, MENU_DATE));
    }

    @Test
    void getNotFound() {
        validateRootCause(NotFoundException.class, () -> menuService.get(RESTAURANT_1_ID, MENU_NOT_FOUND_DATE));
    }

    @Test
    void getByEnabledRestaurantIdAndDate() {
        MENU_TO_MATCHER.assertMatch(menuService.getEnabled(RESTAURANT_1_ID, MENU_DATE), menu_1);
    }

    @Test
    void getByRestaurantIdAndDateNotFound() {
        validateRootCause(NotFoundException.class, () -> menuService.getEnabled(RESTAURANT_1_ID, MENU_NOT_FOUND_DATE));
    }

    @Test
    void getAllEnabled() {
        MENU_TO_MATCHER.assertMatch(menuService.getAllEnabled(RESTAURANT_1_ID), menu_1, menu_1_2, menu_4);
    }
}