package ru.javaops.restaurants.restaurant;

import ru.javaops.restaurants.MatcherFactory;
import ru.javaops.restaurants.restaurant.to.VoteTO;

import java.time.LocalDate;

import static ru.javaops.restaurants.restaurant.RestaurantTestData.RESTAURANT_1_ID;

public class VoteTestData {
    public static final MatcherFactory.Matcher<VoteTO> VOTE_TO_MATCHER = MatcherFactory.usingEqualsComparator(VoteTO.class);
    public static final LocalDate VOTE_DATE = LocalDate.parse("2026-01-07");
    public static final LocalDate VOTE_DATE_NOT_FOUND = LocalDate.parse("2025-01-01");
    public static final VoteTO VOTE_FOR_ID_1 = new VoteTO(RESTAURANT_1_ID);
}
