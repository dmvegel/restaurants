package com.github.dmvegel.restaurants.restaurant.repository;

import com.github.dmvegel.restaurants.restaurant.model.Menu;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Transactional(readOnly = true)
public interface MenuRepository extends JpaRepository<Menu, Integer> {
    @EntityGraph(attributePaths = {"dishes"})
    @Query("SELECT m FROM Menu m " +
            "WHERE m.restaurant.id = :restaurantId AND m.date = :date AND m.enabled = true")
    Optional<Menu> findByRestaurantIdAndDateEnabled(int restaurantId, LocalDate date);

    @EntityGraph(attributePaths = {"dishes"})
    Optional<Menu> findByRestaurantIdAndDate(int restaurantId, LocalDate date);

    @EntityGraph(attributePaths = {"dishes"})
    @Query("SELECT m FROM Menu m " +
            "WHERE m.restaurant.id = :restaurantId AND m.enabled = true ORDER BY m.date DESC")
    List<Menu> findByRestaurantIdOrderByDateDescEnabled(int restaurantId);

    @EntityGraph(attributePaths = {"dishes"})
    List<Menu> findByRestaurantIdOrderByDateDesc(int restaurantId);
}
