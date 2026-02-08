package com.github.dmvegel.restaurants.restaurant.web;

import com.github.dmvegel.restaurants.AbstractControllerTest;
import com.github.dmvegel.restaurants.common.util.JsonUtil;
import com.github.dmvegel.restaurants.restaurant.service.VoteService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDate;

import static com.github.dmvegel.restaurants.restaurant.MenuTestData.MENU_DATE;
import static com.github.dmvegel.restaurants.restaurant.VoteTestData.*;
import static com.github.dmvegel.restaurants.restaurant.web.VoteController.REST_URL;
import static com.github.dmvegel.restaurants.user.UserTestData.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class VoteControllerTest extends AbstractControllerTest {
    private static final String REST_URL_SLASH = REST_URL + '/';
    private static final String REST_URL_RESTAURANTS = REST_URL_SLASH + "restaurants";
    private static final String REST_URL_CURRENT_VOTE = REST_URL_SLASH + MENU_DATE;

    @Autowired
    private VoteService voteService;

    @Test
    @WithUserDetails(USER_MAIL)
    void vote() throws Exception {
        perform(MockMvcRequestBuilders.post(REST_URL_RESTAURANTS)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(VOTE_FOR_ID_1)))
                .andExpect(status().isCreated())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(VOTE_TO_MATCHER.contentJson(VOTE_FOR_ID_1));

        VOTE_TO_MATCHER.assertMatch(voteService.getByUserIdAndDate(USER_ID, LocalDate.now()), VOTE_FOR_ID_1);
    }

    @Test
    @WithUserDetails(USER_MAIL)
    void voteWithoutMenu() throws Exception {
        perform(MockMvcRequestBuilders.post(REST_URL_RESTAURANTS)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(VOTE_FOR_ID_3)))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithUserDetails(USER_MAIL)
    void voteWithoutDishes() throws Exception {
        perform(MockMvcRequestBuilders.post(REST_URL_RESTAURANTS)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(VOTE_FOR_ID_2)))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithUserDetails(USER_MAIL)
    void voteForDisabledRestaurant() throws Exception {
        perform(MockMvcRequestBuilders.post(REST_URL_RESTAURANTS)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(VOTE_FOR_DISABLED)))
                .andExpect(status().isNotFound());
    }

    @Test
    void voteUnauthorized() throws Exception {
        perform(MockMvcRequestBuilders.post(REST_URL_RESTAURANTS)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(VOTE_FOR_ID_1)))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithUserDetails(GUEST_MAIL)
    void voteGuest() throws Exception {
        perform(MockMvcRequestBuilders.post(REST_URL_RESTAURANTS)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(VOTE_FOR_ID_1)))
                .andExpect(status().isForbidden());
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