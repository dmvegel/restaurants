package ru.javaops.restaurants.restaurant;

import ru.javaops.restaurants.restaurant.to.DishTO;

import java.math.BigDecimal;

public class DishTestData {
    public static final DishTO DISH_1 = new DishTO("Pasta Carbonara", BigDecimal.valueOf(850.00), "RUB");
    public static final DishTO DISH_2 = new DishTO("Pizza Margherita", BigDecimal.valueOf(750.00), "RUB");
    public static final DishTO DISH_3 = new DishTO("Salmon Sushi", BigDecimal.valueOf(1200.00), "RUB");
    public static final DishTO DISH_4 = new DishTO("Tuna Roll", BigDecimal.valueOf(950.00), "RUB");
    public static final DishTO DISH_5 = new DishTO("Cheeseburger", BigDecimal.valueOf(650.00), "RUB");
    public static final DishTO DISH_6 = new DishTO("French Fries", BigDecimal.valueOf(300.00), "RUB");
    public static final DishTO DISH_7 = new DishTO("Juice", BigDecimal.valueOf(250.00), "RUB");
    public static final DishTO DISH_8 = new DishTO("Dish", BigDecimal.valueOf(300.00), "RUB");
    public static final DishTO DISH_9 = new DishTO("Another Dish", BigDecimal.valueOf(300.00), "RUB");

    public static final DishTO NEW_DISH_1 = new DishTO("New Dish", BigDecimal.valueOf(1000), "RUB");
}
