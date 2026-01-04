package ru.javaops.bootjava.restaurant.service;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import ru.javaops.bootjava.common.service.BaseService;
import ru.javaops.bootjava.restaurant.model.Restaurant;
import ru.javaops.bootjava.restaurant.model.Vote;
import ru.javaops.bootjava.restaurant.repository.VoteRepository;
import ru.javaops.bootjava.user.model.User;

import java.time.LocalDate;
import java.time.LocalTime;

@Service
public class VoteService extends BaseService<Vote, VoteRepository> {
    private static final LocalTime CHANGE_DEADLINE = LocalTime.of(11, 0);

    public VoteService(VoteRepository repository) {
        super(repository);
    }

    public long countVotes(int restaurantId, LocalDate date) {
        return repository.countByRestaurantIdAndDate(restaurantId, date);
    }

    @Transactional
    public Vote save(User user, Restaurant restaurant, LocalDate date) {
        return repository.findByUserIdAndDate(user.getId(), date)
                .map(existingVote -> {
                    if (!LocalTime.now().isBefore(CHANGE_DEADLINE)) {
                        throw new IllegalStateException("Cannot change vote after " + CHANGE_DEADLINE);
                    }
                    existingVote.setRestaurant(restaurant);
                    return existingVote;
                })
                .orElseGet(() -> repository.save(new Vote(user, restaurant, date)));
    }

}
