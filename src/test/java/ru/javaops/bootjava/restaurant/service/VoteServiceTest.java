package ru.javaops.bootjava.restaurant.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.bean.override.mockito.MockitoSpyBean;
import ru.javaops.bootjava.common.error.NotFoundException;
import ru.javaops.bootjava.common.error.VoteTimeExpiredException;
import ru.javaops.bootjava.common.service.AbstractServiceTest;
import ru.javaops.bootjava.common.time.TimeService;
import ru.javaops.bootjava.restaurant.to.VoteTO;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static ru.javaops.bootjava.common.time.TimeService.CHANGE_DEADLINE;
import static ru.javaops.bootjava.restaurant.RestaurantTestData.*;
import static ru.javaops.bootjava.restaurant.VoteTestData.VOTE_DATE;
import static ru.javaops.bootjava.restaurant.VoteTestData.VOTE_DATE_NOT_FOUND;
import static ru.javaops.bootjava.user.UserTestData.USER_ID;
import static ru.javaops.bootjava.user.UserTestData.user;

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
}