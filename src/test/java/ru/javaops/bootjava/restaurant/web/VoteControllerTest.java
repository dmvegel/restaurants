package ru.javaops.bootjava.restaurant.web;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.javaops.bootjava.AbstractControllerTest;
import ru.javaops.bootjava.restaurant.service.VoteService;

import java.time.LocalDate;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.javaops.bootjava.restaurant.MenuTestData.MENU_DATE;
import static ru.javaops.bootjava.restaurant.RestaurantTestData.*;
import static ru.javaops.bootjava.restaurant.VoteTestData.VOTE_FOR_ID_1;
import static ru.javaops.bootjava.restaurant.VoteTestData.VOTE_TO_MATCHER;
import static ru.javaops.bootjava.restaurant.web.VoteController.REST_URL;
import static ru.javaops.bootjava.user.UserTestData.*;

class VoteControllerTest extends AbstractControllerTest {
    private static final String REST_URL_SLASH = REST_URL + '/';
    private static final String REST_URL_RESTAURANTS_SLASH = REST_URL_SLASH + "restaurants/";
    private static final String REST_URL_RESTAURANTS_ON_MENU_DATE_SLASH = REST_URL_SLASH + MENU_DATE + "/restaurants/";
    private static final String REST_URL_CURRENT_VOTE = REST_URL_SLASH + MENU_DATE + "/me";

    @Autowired
    private VoteService voteService;

    @Test
    @WithUserDetails(USER_MAIL)
    void vote() throws Exception {
        perform(MockMvcRequestBuilders.post(
                REST_URL_RESTAURANTS_SLASH + RESTAURANT_1_ID))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(VOTE_TO_MATCHER.contentJson(VOTE_FOR_ID_1));

        VOTE_TO_MATCHER.assertMatch(voteService.getByUserIdAndDate(USER_ID, LocalDate.now()), VOTE_FOR_ID_1);
    }

    @Test
    @WithUserDetails(USER_MAIL)
    void voteForDisabledRestaurant() throws Exception {
        perform(MockMvcRequestBuilders.post(
                REST_URL_RESTAURANTS_SLASH + DISABLED_RESTAURANT_ID))
                .andExpect(status().isNotFound());
    }

    @Test
    void voteUnauthorized() throws Exception {
        perform(MockMvcRequestBuilders.post(
                REST_URL_RESTAURANTS_SLASH + RESTAURANT_1_ID))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithUserDetails(GUEST_MAIL)
    void voteGuest() throws Exception {
        perform(MockMvcRequestBuilders.post(
                REST_URL_RESTAURANTS_SLASH + RESTAURANT_1_ID))
                .andExpect(status().isForbidden());
    }

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
                REST_URL_RESTAURANTS_ON_MENU_DATE_SLASH + RESTAURANT_1_ID))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(RESTAURANT_VOTES_TO_MATCHER.contentJson(restaurant_1_with_votes));
    }

    @Test
    @WithUserDetails(USER_MAIL)
    void getRestaurantWithVotesNotFound() throws Exception {
        perform(MockMvcRequestBuilders.get(
                REST_URL_RESTAURANTS_ON_MENU_DATE_SLASH + RESTAURANT_NOT_FOUND_ID))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithUserDetails(USER_MAIL)
    void getUserVote() throws Exception {
        perform(MockMvcRequestBuilders.get(
                REST_URL_CURRENT_VOTE))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(VOTE_TO_MATCHER.contentJson(VOTE_FOR_ID_1));
    }

    @Test
    void getUserVoteUnauthorized() throws Exception {
        perform(MockMvcRequestBuilders.get(
                REST_URL_CURRENT_VOTE))
                .andExpect(status().isUnauthorized());
    }
}