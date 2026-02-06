package com.github.dmvegel.restaurants.restaurant.web;

import com.github.dmvegel.restaurants.AbstractControllerTest;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static com.github.dmvegel.restaurants.restaurant.MenuTestData.MENU_DATE;
import static com.github.dmvegel.restaurants.restaurant.RestaurantTestData.*;
import static com.github.dmvegel.restaurants.restaurant.web.VotingResultController.REST_URL;
import static com.github.dmvegel.restaurants.user.UserTestData.USER_MAIL;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class VotingResultControllerTest extends AbstractControllerTest {
    private static final String REST_URL_SLASH = REST_URL + '/';
    private static final String REST_URL_RESTAURANTS_ON_MENU_DATE_SLASH = REST_URL_SLASH + MENU_DATE;

    @Test
    @WithUserDetails(USER_MAIL)
    void getRestaurantsWithVotes() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL_SLASH + MENU_DATE))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(RESTAURANT_VOTES_TO_MATCHER.contentJson(
                        restaurant_1_with_votes,
                        restaurant_2_with_votes,
                        restaurant_3_with_votes
                ));
    }

    @Test
    @WithUserDetails(USER_MAIL)
    void getRestaurantWithVotes() throws Exception {
        perform(MockMvcRequestBuilders.get(
                REST_URL_RESTAURANTS_ON_MENU_DATE_SLASH).param("restaurantId", String.valueOf(RESTAURANT_1_ID)))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(RESTAURANT_VOTES_TO_MATCHER.contentJson(restaurant_1_with_votes));
    }

    @Test
    @WithUserDetails(USER_MAIL)
    void getRestaurantWithVotesNotFound() throws Exception {
        perform(MockMvcRequestBuilders.get(
                        REST_URL_RESTAURANTS_ON_MENU_DATE_SLASH)
                .param("restaurantId", String.valueOf(RESTAURANT_NOT_FOUND_ID)))
                .andExpect(status().isNotFound());
    }
}