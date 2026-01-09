package ru.javaops.bootjava.restaurant;

import ru.javaops.bootjava.MatcherFactory;
import ru.javaops.bootjava.restaurant.to.MenuTO;

import java.time.LocalDate;
import java.util.Set;

import static ru.javaops.bootjava.restaurant.DishTestData.*;

public class MenuTestData {
    public static final MatcherFactory.Matcher<MenuTO> MENU_TO_MATCHER = MatcherFactory.usingRecursiveComparatorWithBigDecimalIgnoringOrder(MenuTO.class);

    public static final int MENU_1_ID = 1;
    public static final int MENU_1_2_ID = 2;
    public static final int MENU_2_ID = 3;
    public static final int MENU_3_ID = 4;
    public static final int MENU_4_ID = 5;
    public static final int MENU_5_ID = 6;
    public static final int MENU_NOT_FOUND_ID = 100;

    public static final LocalDate MENU_DATE = LocalDate.parse("2026-01-07");
    public static final LocalDate MENU_DATE_2 = LocalDate.parse("2026-01-08");
    public static final LocalDate NEW_MENU_DATE = LocalDate.parse("2026-01-09");
    public static final LocalDate MENU_NOT_FOUND_DATE = LocalDate.parse("2025-01-01");

    public static final MenuTO menu_1 = new MenuTO(MENU_1_ID, MENU_DATE, Set.of(DISH_1, DISH_2));
    public static final MenuTO menu_1_2 = new MenuTO(MENU_1_2_ID, MENU_DATE_2, Set.of(DISH_7));
    public static final MenuTO menu_2 = new MenuTO(MENU_2_ID, MENU_DATE, Set.of(DISH_3, DISH_4));
    public static final MenuTO menu_3 = new MenuTO(MENU_3_ID, MENU_DATE, Set.of(DISH_5, DISH_6));
    public static final MenuTO menu_4 = new MenuTO(MENU_4_ID, MENU_DATE, Set.of(DISH_8));
    public static final MenuTO menu_created = new MenuTO(MENU_5_ID, NEW_MENU_DATE, Set.of(NEW_DISH_1));

    public static MenuTO getNew() {
        return new MenuTO(NEW_MENU_DATE, Set.of(NEW_DISH_1));
    }

    public static MenuTO getUpdated() {
        return new MenuTO(MENU_1_ID, MENU_DATE, Set.of(DISH_1, NEW_DISH_1));
    }
}
