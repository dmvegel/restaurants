package ru.javaops.restaurants.restaurant.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import ru.javaops.restaurants.restaurant.model.Restaurant;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Transactional(readOnly = true)
public interface RestaurantRepository extends JpaRepository<Restaurant, Integer> {
    @Query("SELECT DISTINCT r FROM Restaurant r JOIN FETCH r.menus m JOIN FETCH m.dishes WHERE m.date=:date AND r.enabled = true")
    List<Restaurant> getEnabledWithMenusByDate(LocalDate date);

    @Query("SELECT r FROM Restaurant r WHERE r.id=:id AND r.enabled = true")
    Optional<Restaurant> getEnabledById(int id);

    @Query("SELECT r FROM Restaurant r WHERE r.enabled = true")
    List<Restaurant> getAllEnabled();

    @Query("SELECT r FROM Restaurant r WHERE r.id = :id AND r.enabled = true")
    Optional<Restaurant> getReferenceEnabled(int id);
}
