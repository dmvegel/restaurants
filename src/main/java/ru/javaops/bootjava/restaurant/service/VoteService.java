package ru.javaops.bootjava.restaurant.service;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import ru.javaops.bootjava.common.error.NotFoundException;
import ru.javaops.bootjava.common.service.BaseService;
import ru.javaops.bootjava.restaurant.model.Restaurant;
import ru.javaops.bootjava.restaurant.model.Vote;
import ru.javaops.bootjava.restaurant.projection.RestaurantVoteProjection;
import ru.javaops.bootjava.restaurant.repository.VoteRepository;
import ru.javaops.bootjava.restaurant.to.RestaurantTO;
import ru.javaops.bootjava.restaurant.to.RestaurantVotesTO;
import ru.javaops.bootjava.user.model.User;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class VoteService extends BaseService<Vote, VoteRepository> {
    private static final LocalTime CHANGE_DEADLINE = LocalTime.of(11, 0);
    private final RestaurantService restaurantService;

    public VoteService(VoteRepository repository, RestaurantService restaurantService) {
        super(repository);
        this.restaurantService = restaurantService;
    }

    public long countVotes(int restaurantId, LocalDate date) {
        return repository.countByRestaurantIdAndDate(restaurantId, date);
    }

    public List<RestaurantVotesTO> getRestaurantsWithVotes(LocalDate date) {
        List<RestaurantTO> restaurantsTo = restaurantService.getAll();
//        List<RestaurantVoteProjection> votes = repository.countVotesByDate(date);
        Map<Integer, Long> votesMap = repository.countVotesByDate(date).stream()
                .collect(Collectors.toMap(
                        RestaurantVoteProjection::getRestaurantId,
                        RestaurantVoteProjection::getCount
                ));
        return restaurantsTo.stream()
                .map(r -> new RestaurantVotesTO(
                        r.getId(),
                        r.getName(),
                        votesMap.getOrDefault(r.getId(), 0L)
                ))
                .toList();
    }

    public RestaurantVotesTO getRestaurantWithVotes(int restaurantId, LocalDate date) {
        RestaurantTO restaurantTo = restaurantService.get(restaurantId);
        long votesCount = repository.countByRestaurantIdAndDate(restaurantId, date);
        return new RestaurantVotesTO(
                restaurantTo.getId(),
                restaurantTo.getName(),
                votesCount
        );
    }

    public Integer getRestaurantId(Integer userId, LocalDate date) {
        return repository.findByUserIdAndDate(userId, date).map(vote -> vote.getRestaurant().getId())
                .orElseThrow(() -> new NotFoundException("Vote didn't exist"));
    }

    @Transactional
    public Vote save(User user, int restaurantId) {
        Restaurant restaurant = restaurantService.getReference(restaurantId);
        LocalDate currentDate = LocalDate.now();
        return repository.findByUserIdAndDate(user.getId(), currentDate)
                .map(existingVote -> {
                    if (!LocalTime.now().isBefore(CHANGE_DEADLINE)) {
                        throw new IllegalStateException("Cannot change vote after " + CHANGE_DEADLINE);
                    }
                    existingVote.setRestaurant(restaurant);
                    return existingVote;
                })
                .orElseGet(() -> repository.save(new Vote(user, restaurant, currentDate)));
    }

}
