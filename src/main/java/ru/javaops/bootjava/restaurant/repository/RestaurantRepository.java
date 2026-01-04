package ru.javaops.bootjava.restaurant.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import ru.javaops.bootjava.common.BaseRepository;
import ru.javaops.bootjava.restaurant.model.Restaurant;

import java.time.LocalDate;
import java.util.Optional;

@Transactional(readOnly = true)
public interface RestaurantRepository extends BaseRepository<Restaurant> {
    @Query("SELECT r FROM Restaurant r LEFT JOIN FETCH r.menus m LEFT JOIN FETCH m.dishes WHERE r.id=:restaurantId")
    Optional<Restaurant> getRestaurantWithAllMenusAndDishes(int restaurantId);

    @Query("SELECT r FROM Restaurant r LEFT JOIN FETCH r.menus AS m WHERE r.id=:restaurantId AND m.date=:date")
    Optional<Restaurant> getRestaurantWithMenuAndDishes(int restaurantId, LocalDate date);
}
