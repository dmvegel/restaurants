package ru.javaops.bootjava.restaurant.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.javaops.bootjava.common.error.NotFoundException;
import ru.javaops.bootjava.common.service.AbstractServiceTest;
import ru.javaops.bootjava.restaurant.to.AdminRestaurantTO;
import ru.javaops.bootjava.restaurant.to.RestaurantTO;
import ru.javaops.bootjava.restaurant.util.RestaurantUtil;

import static ru.javaops.bootjava.restaurant.MenuTestData.MENU_DATE;
import static ru.javaops.bootjava.restaurant.RestaurantTestData.*;

class RestaurantServiceTest extends AbstractServiceTest {
    @Autowired
    RestaurantService restaurantService;

    @Test
    void getEnabled() {
        RESTAURANT_TO_MATCHER.assertMatch(restaurantService.getEnabled(RESTAURANT_1_ID), restaurant_1);
    }

    @Test
    void getDisabled() {
        validateRootCause(NotFoundException.class, () -> restaurantService.getEnabled(DISABLED_RESTAURANT_ID));
    }

    @Test
    void getWithMenuEnabled() {
        RESTAURANT_WITH_MENUS_TO_MATCHER.assertMatch(
                restaurantService.getEnabledWithMenu(RESTAURANT_1_ID, MENU_DATE), restaurant_1_on_menu1_date);
    }

    @Test
    void getWithMenuDisabled() {
        validateRootCause(NotFoundException.class, () -> restaurantService.getEnabledWithMenu(DISABLED_RESTAURANT_ID, MENU_DATE));
    }

    @Test
    void create() {
        AdminRestaurantTO created = restaurantService.create(getNew());
        AdminRestaurantTO adminRestaurantTO = RestaurantUtil.getAdminTo(getNew(), true);
        adminRestaurantTO.setId(created.getId());
        ADMIN_RESTAURANT_TO_MATCHER.assertMatch(restaurantService.get(created.getId()), adminRestaurantTO);
    }

    @Test
    void update() {
        restaurantService.update(getUpdated());
        AdminRestaurantTO fetched = restaurantService.get(RESTAURANT_1_ID);
        AdminRestaurantTO adminRestaurantTO = RestaurantUtil.getAdminTo(getUpdated(), true);
        ADMIN_RESTAURANT_TO_MATCHER.assertMatch(fetched, adminRestaurantTO);
    }

    @Test
    void updateDisabled() {
        RestaurantTO restaurantTO = getUpdated();
        restaurantTO.setId(DISABLED_RESTAURANT_ID);
        restaurantService.update(restaurantTO);
        AdminRestaurantTO fetched = restaurantService.get(DISABLED_RESTAURANT_ID);
        AdminRestaurantTO adminRestaurantTO = RestaurantUtil.getAdminTo(restaurantTO, false);
        ADMIN_RESTAURANT_TO_MATCHER.assertMatch(fetched, adminRestaurantTO);
    }

    @Test
    void updateNotExist() {
        RestaurantTO restaurantTo = getUpdated();
        restaurantTo.setId(RESTAURANT_NOT_FOUND_ID);
        validateRootCause(NotFoundException.class, () -> restaurantService.update(restaurantTo));
    }

    @Test
    void getAllWithMenusEnabled() {
        RESTAURANT_WITH_MENUS_TO_MATCHER
                .assertMatch(restaurantService.getAllEnabledWithMenu(MENU_DATE), restaurant_1_on_menu1_date, restaurant_2_with_menu, restaurant_3_with_menu);
    }

    @Test
    void getNotFound() {
        validateRootCause(NotFoundException.class, () -> restaurantService.get(RESTAURANT_NOT_FOUND_ID));
    }

    @Test
    void disable() {
        restaurantService.enable(RESTAURANT_1_ID, false);
        validateRootCause(NotFoundException.class, () -> restaurantService.getEnabled(RESTAURANT_1_ID));
    }

    @Test
    void getAll() {
        ADMIN_RESTAURANT_TO_MATCHER.assertMatch(restaurantService.getAll(),
                admin_restaurant_1,
                admin_restaurant_2,
                admin_restaurant_3,
                admin_disabled_restaurant);
    }
}