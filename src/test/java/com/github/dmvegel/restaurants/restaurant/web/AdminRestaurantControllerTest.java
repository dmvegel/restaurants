package com.github.dmvegel.restaurants.restaurant.web;

import com.github.dmvegel.restaurants.AbstractControllerTest;
import com.github.dmvegel.restaurants.common.util.JsonUtil;
import com.github.dmvegel.restaurants.restaurant.RestaurantTestData;
import com.github.dmvegel.restaurants.restaurant.service.RestaurantService;
import com.github.dmvegel.restaurants.restaurant.to.AdminRestaurantTO;
import com.github.dmvegel.restaurants.restaurant.to.RestaurantTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static com.github.dmvegel.restaurants.restaurant.RestaurantTestData.*;
import static com.github.dmvegel.restaurants.restaurant.web.AdminRestaurantController.REST_URL;
import static com.github.dmvegel.restaurants.user.UserTestData.*;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class AdminRestaurantControllerTest extends AbstractControllerTest {
    private static final String REST_URL_SLASH = REST_URL + '/';

    @Autowired
    private RestaurantService restaurantService;

    @Test
    @WithUserDetails(ADMIN_MAIL)
    void get() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL_SLASH + RESTAURANT_1_ID))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(ADMIN_RESTAURANT_TO_MATCHER.contentJson(admin_restaurant_1));
    }

    @Test
    @WithUserDetails(ADMIN_MAIL)
    void getDisabled() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL_SLASH + DISABLED_RESTAURANT_ID))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(ADMIN_RESTAURANT_TO_MATCHER.contentJson(admin_disabled_restaurant));
    }

    @Test
    @WithUserDetails(ADMIN_MAIL)
    void getNotFound() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL_SLASH + NOT_FOUND))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithUserDetails(ADMIN_MAIL)
    void getAll() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(ADMIN_RESTAURANT_TO_MATCHER.contentJson(
                        admin_restaurant_1,
                        admin_restaurant_2,
                        admin_restaurant_3,
                        admin_disabled_restaurant
                ));
    }

    @Test
    @WithUserDetails(USER_MAIL)
    void getForbidden() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithUserDetails(ADMIN_MAIL)
    void create() throws Exception {
        RestaurantTO newRestaurant = RestaurantTestData.getNew();

        ResultActions action = perform(MockMvcRequestBuilders.post(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(newRestaurant)))
                .andExpect(ADMIN_RESTAURANT_TO_MATCHER.contentJson(admin_restaurant_created))
                .andExpect(status().isCreated());

        AdminRestaurantTO created = ADMIN_RESTAURANT_TO_MATCHER.readFromJson(action);

        ADMIN_RESTAURANT_TO_MATCHER.assertMatch(
                restaurantService.get(created.getId()),
                admin_restaurant_created
        );
    }

    @Test
    @WithUserDetails(ADMIN_MAIL)
    void createInvalid() throws Exception {
        RestaurantTO invalid = new RestaurantTO(null, "");

        perform(MockMvcRequestBuilders.post(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(invalid)))
                .andExpect(status().isUnprocessableContent());
    }

    @Test
    @WithUserDetails(ADMIN_MAIL)
    void update() throws Exception {
        RestaurantTO updated = RestaurantTestData.getUpdated();

        perform(MockMvcRequestBuilders.put(REST_URL_SLASH + RESTAURANT_1_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(updated)))
                .andExpect(status().isNoContent());

        ADMIN_RESTAURANT_TO_MATCHER.assertMatch(
                restaurantService.get(RESTAURANT_1_ID),
                admin_restaurant_updated
        );
    }

    @Test
    @WithUserDetails(ADMIN_MAIL)
    void updateIdNotConsistent() throws Exception {
        RestaurantTO updated = RestaurantTestData.getUpdated();
        updated.setId(NOT_FOUND);

        perform(MockMvcRequestBuilders.put(REST_URL_SLASH + RESTAURANT_1_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(updated)))
                .andExpect(status().isUnprocessableContent());
    }

    @Test
    @WithUserDetails(ADMIN_MAIL)
    void disable() throws Exception {
        perform(MockMvcRequestBuilders.patch(REST_URL_SLASH + RESTAURANT_1_ID)
                .param("enabled", "false"))
                .andExpect(status().isNoContent());

        assertFalse(restaurantService.get(RESTAURANT_1_ID).isEnabled());
    }

    @Test
    @WithUserDetails(ADMIN_MAIL)
    void enableNotFound() throws Exception {
        perform(MockMvcRequestBuilders.patch(REST_URL_SLASH + NOT_FOUND)
                .param("enabled", "false"))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithUserDetails(USER_MAIL)
    void deleteForbidden() throws Exception {
        perform(MockMvcRequestBuilders.delete(REST_URL_SLASH + RESTAURANT_1_ID))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithUserDetails(ADMIN_MAIL)
    void delete() throws Exception {
        perform(MockMvcRequestBuilders.delete(REST_URL_SLASH + RESTAURANT_1_ID))
                .andExpect(status().isNoContent());
        ADMIN_RESTAURANT_TO_MATCHER.assertMatch(restaurantService.getAll(),
                admin_restaurant_2,
                admin_restaurant_3,
                admin_disabled_restaurant);
    }
}