package ru.javaops.bootjava.restaurant.repository;

import org.springframework.transaction.annotation.Transactional;
import ru.javaops.bootjava.common.BaseRepository;
import ru.javaops.bootjava.restaurant.model.Vote;

import java.time.LocalDate;
import java.util.Optional;

@Transactional(readOnly = true)
public interface VoteRepository extends BaseRepository<Vote> {
    long countByRestaurantIdAndDate(Integer restaurantId, LocalDate date);
    Optional<Vote> findByUserIdAndDate(Integer userId, LocalDate date);
}
