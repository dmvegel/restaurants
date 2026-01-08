package ru.javaops.bootjava.restaurant.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import ru.javaops.bootjava.common.BaseRepository;
import ru.javaops.bootjava.restaurant.model.Menu;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Transactional(readOnly = true)
public interface MenuRepository extends BaseRepository<Menu> {
    @EntityGraph(attributePaths = {"dishes"})
    @Query("SELECT m FROM Menu m JOIN m.restaurant r " +
            "WHERE r.id = :restaurantId AND m.date = :date AND r.enabled = true")
    Optional<Menu> findByRestaurantIdAndDateEnabled(int restaurantId, LocalDate date);

    @EntityGraph(attributePaths = {"dishes"})
    Optional<Menu> findByRestaurantIdAndDate(int restaurantId, LocalDate date);

    @EntityGraph(attributePaths = {"dishes"})
    @Query("SELECT m FROM Menu m JOIN m.restaurant r " +
            "WHERE r.id = :restaurantId AND r.enabled = true ORDER BY m.date DESC")
    List<Menu> findByRestaurantIdOrderByDateDescEnabled(int restaurantId);

    @EntityGraph(attributePaths = {"dishes"})
    List<Menu> findByRestaurantIdOrderByDateDesc(int restaurantId);
}
