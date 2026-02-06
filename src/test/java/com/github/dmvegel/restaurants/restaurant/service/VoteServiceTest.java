package com.github.dmvegel.restaurants.restaurant.service;

import com.github.dmvegel.restaurants.common.error.NotFoundException;
import com.github.dmvegel.restaurants.common.error.VoteTimeExpiredException;
import com.github.dmvegel.restaurants.common.service.AbstractServiceTest;
import com.github.dmvegel.restaurants.common.time.TimeService;
import com.github.dmvegel.restaurants.restaurant.to.VoteTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.bean.override.mockito.MockitoSpyBean;

import java.time.LocalDate;

import static com.github.dmvegel.restaurants.common.time.TimeService.CHANGE_DEADLINE;
import static com.github.dmvegel.restaurants.restaurant.RestaurantTestData.*;
import static com.github.dmvegel.restaurants.restaurant.VoteTestData.*;
import static com.github.dmvegel.restaurants.user.UserTestData.USER_ID;
import static com.github.dmvegel.restaurants.user.UserTestData.user;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

class VoteServiceTest extends AbstractServiceTest {
    @Autowired
    VoteService voteService;

    @MockitoSpyBean
    private TimeService timeService;

    @Test
    void voteForEnabled() {
        VoteTO voteTo = voteService.save(user, RESTAURANT_1_ID);
        assertThat(voteService.getByUserIdAndDate(user.getId(), LocalDate.now()))
                .isEqualTo(voteTo);
    }

    @Test
    void voteForDisabled() {
        validateRootCause(NotFoundException.class, () -> voteService.save(user, DISABLED_RESTAURANT_ID));
    }

    @Test
    void changeVoteBeforeDeadline() {
        when(timeService.now()).thenReturn(CHANGE_DEADLINE.minusMinutes(1));
        VoteTO saved = voteService.save(user, RESTAURANT_1_ID);
        assertThat(saved.restaurantId()).isEqualTo(RESTAURANT_1_ID);
        VoteTO changed = voteService.save(user, RESTAURANT_1_ID);
        assertThat(changed.restaurantId()).isEqualTo(RESTAURANT_1_ID);
    }

    @Test
    void changeVoteAfterDeadline() {
        when(timeService.now()).thenReturn(CHANGE_DEADLINE.plusMinutes(1));
        VoteTO saved = voteService.save(user, RESTAURANT_1_ID);
        assertThat(saved.restaurantId()).isEqualTo(RESTAURANT_1_ID);
        validateRootCause(VoteTimeExpiredException.class, () -> voteService.save(user, RESTAURANT_1_ID));
    }

    @Test
    void getRestaurantsWithVotes() {
        RESTAURANT_VOTES_TO_MATCHER.assertMatch(voteService.getRestaurantsWithVotes(VOTE_DATE),
                restaurant_1_with_votes, restaurant_2_with_votes, restaurant_3_with_votes);
    }

    @Test
    void getRestaurantWithVotes() {
        RESTAURANT_VOTES_TO_MATCHER
                .assertMatch(voteService.getRestaurantWithVotes(RESTAURANT_1_ID, VOTE_DATE), restaurant_1_with_votes);
    }

    @Test
    void getNotFound() {
        validateRootCause(NotFoundException.class, () -> voteService.getByUserIdAndDate(USER_ID, VOTE_DATE_NOT_FOUND));
    }

    @Test
    void getByUserId() {
        VOTE_HISTORY_TO_MATCHER.assertMatch(voteService.getByUserId(USER_ID), VOTE_HISTORY_ID_1_DATE_2, VOTE_HISTORY_ID_1_DATE_1);
    }
}