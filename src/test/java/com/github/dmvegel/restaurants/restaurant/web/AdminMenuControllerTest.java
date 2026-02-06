package com.github.dmvegel.restaurants.restaurant.web;

import com.github.dmvegel.restaurants.AbstractControllerTest;
import com.github.dmvegel.restaurants.common.util.JsonUtil;
import com.github.dmvegel.restaurants.restaurant.MenuTestData;
import com.github.dmvegel.restaurants.restaurant.service.MenuService;
import com.github.dmvegel.restaurants.restaurant.to.MenuTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDate;

import static com.github.dmvegel.restaurants.restaurant.MenuTestData.*;
import static com.github.dmvegel.restaurants.restaurant.RestaurantTestData.RESTAURANT_1_ID;
import static com.github.dmvegel.restaurants.restaurant.RestaurantTestData.RESTAURANT_NOT_FOUND_ID;
import static com.github.dmvegel.restaurants.restaurant.web.AdminMenuController.REST_URL;
import static com.github.dmvegel.restaurants.user.UserTestData.ADMIN_MAIL;
import static com.github.dmvegel.restaurants.user.UserTestData.USER_MAIL;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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
        perform(MockMvcRequestBuilders.post(REST_URL_SLASH_FOR_ID_1 + NEW_MENU_DATE)
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
        perform(MockMvcRequestBuilders.post(REST_URL_SLASH_FOR_ID_1 + NEW_MENU_DATE)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(new MenuTO(null))))
                .andExpect(status().isUnprocessableContent());
    }

    @Test
    @WithUserDetails(ADMIN_MAIL)
    void update() throws Exception {
        perform(MockMvcRequestBuilders.put(REST_URL_SLASH_FOR_ID_1 + MENU_DATE)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(MenuTestData.getUpdated())))
                .andExpect(status().isNoContent());

        MENU_TO_MATCHER.assertMatch(menuService.get(RESTAURANT_1_ID, MENU_DATE), MenuTestData.getUpdated());
    }

    @Test
    @WithUserDetails(ADMIN_MAIL)
    void updateNotFound() throws Exception {
        perform(MockMvcRequestBuilders.put(
                        REST_URL.replace("{restaurantId}",
                                String.valueOf(RESTAURANT_NOT_FOUND_ID)) + MENU_DATE)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(MenuTestData.getUpdated())))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithUserDetails(USER_MAIL)
    void deleteForbidden() throws Exception {
        perform(MockMvcRequestBuilders.delete(REST_URL_SLASH_FOR_ID_1 + MENU_DATE))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithUserDetails(ADMIN_MAIL)
    void delete() throws Exception {
        perform(MockMvcRequestBuilders.delete(REST_URL_SLASH_FOR_ID_1 + LocalDate.now()))
                .andExpect(status().isNoContent());
        MENU_TO_MATCHER.assertMatch(menuService.getAll(RESTAURANT_1_ID), menu_1, menu_1_2);

    }
}