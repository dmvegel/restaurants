package ru.javaops.bootjava.restaurant.web;

import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.javaops.bootjava.AbstractControllerTest;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.javaops.bootjava.restaurant.MenuTestData.*;
import static ru.javaops.bootjava.restaurant.RestaurantTestData.DISABLED_RESTAURANT_ID;
import static ru.javaops.bootjava.restaurant.RestaurantTestData.RESTAURANT_1_ID;
import static ru.javaops.bootjava.restaurant.web.MenuController.REST_URL;
import static ru.javaops.bootjava.user.UserTestData.USER_MAIL;

class MenuControllerTest extends AbstractControllerTest {
    private static final String REST_URL_SLASH_FOR_ID_1 =
            REST_URL.replace("{restaurantId}", String.valueOf(RESTAURANT_1_ID)) + '/';

    private static final String REST_URL_FOR_ID_1 =
            REST_URL.replace("{restaurantId}", String.valueOf(RESTAURANT_1_ID));

    @Test
    @WithUserDetails(USER_MAIL)
    void getEnabled() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL_SLASH_FOR_ID_1 + MENU_DATE))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MENU_TO_MATCHER.contentJson(menu_1));
    }

    @Test
    @WithUserDetails(USER_MAIL)
    void getDisabledRestaurant() throws Exception {
        perform(MockMvcRequestBuilders.get(
                REST_URL.replace("{restaurantId}", String.valueOf(DISABLED_RESTAURANT_ID)) +
                        "/" + MENU_DATE))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithUserDetails(USER_MAIL)
    void getNotFound() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL_SLASH_FOR_ID_1 + MENU_NOT_FOUND_DATE))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithUserDetails(USER_MAIL)
    void getAllEnabled() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL_FOR_ID_1))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MENU_TO_MATCHER.contentJson(
                        menu_4,
                        menu_1_2,
                        menu_1
                ));
    }

    @Test
    void getUnauthorized() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL_FOR_ID_1))
                .andExpect(status().isUnauthorized());
    }
}