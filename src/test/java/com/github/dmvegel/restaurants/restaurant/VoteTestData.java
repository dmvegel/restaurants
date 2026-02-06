package com.github.dmvegel.restaurants.restaurant;

import com.github.dmvegel.restaurants.MatcherFactory;
import com.github.dmvegel.restaurants.restaurant.to.VoteHistoryTO;
import com.github.dmvegel.restaurants.restaurant.to.VoteTO;

import java.time.LocalDate;

import static com.github.dmvegel.restaurants.restaurant.RestaurantTestData.*;

public class VoteTestData {
    public static final MatcherFactory.Matcher<VoteTO> VOTE_TO_MATCHER = MatcherFactory.usingEqualsComparator(VoteTO.class);
    public static final MatcherFactory.Matcher<VoteHistoryTO> VOTE_HISTORY_TO_MATCHER = MatcherFactory.usingIgnoringFieldsComparator(VoteHistoryTO.class);
    public static final LocalDate VOTE_DATE = LocalDate.parse("2026-01-07");
    public static final LocalDate VOTE_DATE_2 = LocalDate.parse("2026-01-08");
    public static final LocalDate VOTE_DATE_NOT_FOUND = LocalDate.parse("2025-01-01");
    public static final VoteTO VOTE_FOR_ID_1 = new VoteTO(RESTAURANT_1_ID);
    public static final VoteTO VOTE_FOR_ID_2 = new VoteTO(RESTAURANT_2_ID);
    public static final VoteTO VOTE_FOR_ID_3 = new VoteTO(RESTAURANT_3_ID);
    public static final VoteTO VOTE_FOR_DISABLED = new VoteTO(DISABLED_RESTAURANT_ID);
    public static final VoteHistoryTO VOTE_HISTORY_ID_1_DATE_1 = new VoteHistoryTO(RESTAURANT_1_ID, VOTE_DATE);
    public static final VoteHistoryTO VOTE_HISTORY_ID_1_DATE_2 = new VoteHistoryTO(RESTAURANT_1_ID, VOTE_DATE_2);
}
