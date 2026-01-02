package ru.javaops.bootjava.restaurant.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.transaction.annotation.Transactional;
import ru.javaops.bootjava.common.BaseRepository;
import ru.javaops.bootjava.restaurant.model.Menu;

import java.time.LocalDate;
import java.util.Optional;

@Transactional(readOnly = true)
public interface MenuRepository extends BaseRepository<Menu> {
    @EntityGraph(attributePaths = {"dishes"})
    Optional<Menu> findByRestaurantIdAndDate(Integer restaurantId, LocalDate date);
}
