package ru.javaops.restaurants.restaurant.web;

import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.javaops.restaurants.AbstractControllerTest;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.javaops.restaurants.restaurant.MenuTestData.MENU_DATE;
import static ru.javaops.restaurants.restaurant.RestaurantTestData.*;
import static ru.javaops.restaurants.restaurant.web.RestaurantController.REST_URL;
import static ru.javaops.restaurants.user.UserTestData.USER_MAIL;

class RestaurantControllerTest extends AbstractControllerTest {
    private static final String REST_URL_SLASH = REST_URL + '/';

    @Test
    @WithUserDetails(USER_MAIL)
    void getEnabled() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL_SLASH + RESTAURANT_1_ID))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(RESTAURANT_TO_MATCHER.contentJson(restaurant_1));
    }

    @Test
    @WithUserDetails(USER_MAIL)
    void getDisabled() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL_SLASH + DISABLED_RESTAURANT_ID))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithUserDetails(USER_MAIL)
    void getNotFound() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL_SLASH + RESTAURANT_NOT_FOUND_ID))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithUserDetails(USER_MAIL)
    void getAllEnabled() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(RESTAURANT_TO_MATCHER.contentJson(restaurant_1, restaurant_2, restaurant_3));
    }

    @Test
    @WithUserDetails(USER_MAIL)
    void getAllEnabledWithMenus() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + "/with-menus")
                .param("date", MENU_DATE.toString()))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(RESTAURANT_WITH_MENUS_TO_MATCHER.contentJson(
                        restaurant_1_on_menu1_date,
                        restaurant_2_with_menu,
                        restaurant_3_with_menu
                ));
    }

    @Test
    void getUnauthorized() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL))
                .andExpect(status().isUnauthorized());
    }
}