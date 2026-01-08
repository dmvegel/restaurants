package ru.javaops.bootjava.restaurant;

import ru.javaops.bootjava.MatcherFactory;
import ru.javaops.bootjava.restaurant.to.AdminRestaurantTO;
import ru.javaops.bootjava.restaurant.to.RestaurantTO;
import ru.javaops.bootjava.restaurant.to.RestaurantVotesTO;
import ru.javaops.bootjava.restaurant.to.RestaurantWithMenuTO;

import java.util.Set;

import static ru.javaops.bootjava.restaurant.MenuTestData.*;

public class RestaurantTestData {
    public static final MatcherFactory.Matcher<AdminRestaurantTO> ADMIN_RESTAURANT_TO_MATCHER = MatcherFactory.usingIgnoringFieldsComparator(AdminRestaurantTO.class);
    public static final MatcherFactory.Matcher<RestaurantTO> RESTAURANT_TO_MATCHER = MatcherFactory.usingEqualsComparator(RestaurantTO.class);
    public static final MatcherFactory.Matcher<RestaurantWithMenuTO> RESTAURANT_WITH_MENUS_TO_MATCHER = MatcherFactory.usingRecursiveComparatorWithBigDecimalIgnoringOrder(RestaurantWithMenuTO.class);
    public static final MatcherFactory.Matcher<RestaurantVotesTO> RESTAURANT_VOTES_TO_MATCHER = MatcherFactory.usingEqualsComparatorIgnoringOrder(RestaurantVotesTO.class);

    public static final int RESTAURANT_1_ID = 1;
    public static final int RESTAURANT_2_ID = 2;
    public static final int RESTAURANT_3_ID = 3;
    public static final int RESTAURANT_4_ID = 4;
    public static final int RESTAURANT_NOT_FOUND_ID = 100;

    public static final RestaurantWithMenuTO restaurant_1_on_menu1_date = new RestaurantWithMenuTO(RESTAURANT_1_ID, "Italian Bistro", Set.of(menu_1));
    public static final RestaurantWithMenuTO restaurant_2_with_menu = new RestaurantWithMenuTO(RESTAURANT_2_ID, "Sushi Place", Set.of(menu_2));
    public static final RestaurantWithMenuTO restaurant_3_with_menu = new RestaurantWithMenuTO(RESTAURANT_3_ID, "Burger House", Set.of(menu_3));
    public static final RestaurantWithMenuTO disabled_restaurant_with_menu = new RestaurantWithMenuTO(RESTAURANT_4_ID, "Disabled Restaurant", Set.of(menu_3));

    public static final RestaurantVotesTO restaurant_1_with_votes = new RestaurantVotesTO(RESTAURANT_1_ID, "Italian Bistro", 1);
    public static final RestaurantVotesTO restaurant_2_with_votes = new RestaurantVotesTO(RESTAURANT_2_ID, "Sushi Place", 1);
    public static final RestaurantVotesTO restaurant_3_with_votes = new RestaurantVotesTO(RESTAURANT_3_ID, "Burger House", 0);

    public static final RestaurantTO restaurant_1 = new RestaurantTO(RESTAURANT_1_ID, "Italian Bistro");
    public static final RestaurantTO restaurant_2 = new RestaurantTO(RESTAURANT_2_ID, "Sushi Place");
    public static final RestaurantTO restaurant_3 = new RestaurantTO(RESTAURANT_3_ID, "Burger House");

    public static RestaurantTO getNew() {
        return new RestaurantTO(null, "New Restaurant");
    }

    public static RestaurantTO getUpdated() {
        return new RestaurantTO(RESTAURANT_1_ID, "UpdatedName");
    }
}
