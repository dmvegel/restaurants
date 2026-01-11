package ru.javaops.restaurants.restaurant.web;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.javaops.restaurants.AbstractControllerTest;
import ru.javaops.restaurants.common.util.JsonUtil;
import ru.javaops.restaurants.restaurant.MenuTestData;
import ru.javaops.restaurants.restaurant.service.MenuService;
import ru.javaops.restaurants.restaurant.to.MenuTO;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.javaops.restaurants.restaurant.MenuTestData.*;
import static ru.javaops.restaurants.restaurant.RestaurantTestData.RESTAURANT_1_ID;
import static ru.javaops.restaurants.restaurant.RestaurantTestData.RESTAURANT_NOT_FOUND_ID;
import static ru.javaops.restaurants.restaurant.web.AdminMenuController.REST_URL;
import static ru.javaops.restaurants.user.UserTestData.ADMIN_MAIL;
import static ru.javaops.restaurants.user.UserTestData.USER_MAIL;

class AdminMenuControllerTest extends AbstractControllerTest {
    private static final String REST_URL_SLASH_FOR_ID_1 =
            REST_URL.replace("{restaurantId}", String.valueOf(RESTAURANT_1_ID)) + '/';

    private static final String REST_URL_FOR_ID_1 = REST_URL
            .replace("{restaurantId}", String.valueOf(RESTAURANT_1_ID));

    @Autowired
    private MenuService menuService;

    @Test
    @WithUserDetails(ADMIN_MAIL)
    void get() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL_SLASH_FOR_ID_1 + MENU_DATE))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MENU_TO_MATCHER.contentJson(menu_1));
    }

    @Test
    @WithUserDetails(ADMIN_MAIL)
    void getNotFound() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL_SLASH_FOR_ID_1 + MENU_NOT_FOUND_DATE))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithUserDetails(ADMIN_MAIL)
    void getAll() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL_FOR_ID_1))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MENU_TO_MATCHER.contentJson(menu_1, menu_1_2, menu_4));
    }

    @Test
    @WithUserDetails(USER_MAIL)
    void getForbidden() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL_SLASH_FOR_ID_1 + MENU_DATE))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithUserDetails(ADMIN_MAIL)
    void create() throws Exception {
        perform(MockMvcRequestBuilders.post(REST_URL_FOR_ID_1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(MenuTestData.getNew())))
                .andExpect(status().isCreated())
                .andExpect(MENU_TO_MATCHER.contentJson(menu_created));

        MENU_TO_MATCHER.assertMatch(
                menuService.get(RESTAURANT_1_ID, NEW_MENU_DATE),
                menu_created
        );
    }

    @Test
    @WithUserDetails(ADMIN_MAIL)
    void createInvalid() throws Exception {
        perform(MockMvcRequestBuilders.post(REST_URL_FOR_ID_1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(new MenuTO(null, null, null))))
                .andExpect(status().isUnprocessableContent());
    }

    @Test
    @WithUserDetails(ADMIN_MAIL)
    void update() throws Exception {
        perform(MockMvcRequestBuilders.put(REST_URL_FOR_ID_1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(MenuTestData.getUpdated())))
                .andExpect(status().isNoContent());

        MENU_TO_MATCHER.assertMatch(menuService.get(RESTAURANT_1_ID, MENU_DATE), MenuTestData.getUpdated());
    }

    @Test
    @WithUserDetails(ADMIN_MAIL)
    void updateNotFound() throws Exception {
        perform(MockMvcRequestBuilders.put(
                        REST_URL.replace("{restaurantId}", String.valueOf(RESTAURANT_NOT_FOUND_ID)))
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(MenuTestData.getUpdated())))
                .andExpect(status().isNotFound());
    }
}