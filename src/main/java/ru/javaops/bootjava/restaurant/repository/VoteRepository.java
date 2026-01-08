package ru.javaops.bootjava.restaurant.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import ru.javaops.bootjava.common.BaseRepository;
import ru.javaops.bootjava.restaurant.model.Vote;
import ru.javaops.bootjava.restaurant.projection.RestaurantVoteProjection;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Transactional(readOnly = true)
public interface VoteRepository extends BaseRepository<Vote> {
    long countByRestaurantIdAndDate(Integer restaurantId, LocalDate date);
    Optional<Vote> findByUserIdAndDate(int userId, LocalDate date);
    @Query("SELECT v.restaurant.id AS restaurantId, COUNT(v.id) AS count " +
            "FROM Vote v " +
            "WHERE v.date = :date " +
            "GROUP BY v.restaurant.id")
    List<RestaurantVoteProjection> countVotesByDate(LocalDate date);
}
