package ru.javaops.restaurants.restaurant.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import ru.javaops.restaurants.restaurant.model.Vote;
import ru.javaops.restaurants.restaurant.projection.RestaurantVoteProjection;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Transactional(readOnly = true)
public interface VoteRepository extends JpaRepository<Vote, Integer> {
    long countByDateAndRestaurantId(LocalDate date, Integer restaurantId);

    Optional<Vote> findByUserIdAndDate(int userId, LocalDate date);

    @Query("SELECT v.restaurant.id AS restaurantId, COUNT(v.id) AS count " +
            "FROM Vote v " +
            "WHERE v.date = :date " +
            "GROUP BY v.restaurant.id")
    List<RestaurantVoteProjection> countVotesByDate(LocalDate date);
}
