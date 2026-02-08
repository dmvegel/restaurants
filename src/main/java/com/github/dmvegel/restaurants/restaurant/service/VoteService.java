package com.github.dmvegel.restaurants.restaurant.service;

import com.github.dmvegel.restaurants.common.error.NotFoundException;
import com.github.dmvegel.restaurants.common.error.VoteTimeExpiredException;
import com.github.dmvegel.restaurants.common.service.BaseService;
import com.github.dmvegel.restaurants.common.time.TimeProvider;
import com.github.dmvegel.restaurants.restaurant.model.Restaurant;
import com.github.dmvegel.restaurants.restaurant.model.Vote;
import com.github.dmvegel.restaurants.restaurant.projection.RestaurantVoteProjection;
import com.github.dmvegel.restaurants.restaurant.repository.VoteRepository;
import com.github.dmvegel.restaurants.restaurant.to.*;
import com.github.dmvegel.restaurants.user.model.User;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.github.dmvegel.restaurants.common.time.TimeProvider.CHANGE_DEADLINE;

@Service
public class VoteService extends BaseService<Vote, VoteRepository> {
    private final RestaurantService restaurantService;
    private final MenuService menuService;
    private final TimeProvider timeProvider;

    public VoteService(VoteRepository repository,
                       RestaurantService restaurantService,
                       TimeProvider timeProvider,
                       MenuService menuService) {
        super(repository);
        this.restaurantService = restaurantService;
        this.timeProvider = timeProvider;
        this.menuService = menuService;
    }

    public List<RestaurantVotesTO> getRestaurantsWithVotes(LocalDate date) {
        List<RestaurantTO> restaurantsTo = restaurantService.getAllEnabled();
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
        RestaurantTO restaurantTo = restaurantService.getEnabled(restaurantId);
        long votesCount = repository.countByDateAndRestaurantId(date, restaurantId);
        return new RestaurantVotesTO(
                restaurantTo.getId(),
                restaurantTo.getName(),
                votesCount
        );
    }

    public VoteTO getByUserIdAndDate(Integer userId, LocalDate date) {
        return new VoteTO(getOrNotFound(() -> repository.findByUserIdAndDate(userId, date),
                "Vote didn't exist").getRestaurant().getId());
    }

    public List<VoteHistoryTO> getByUserId(Integer userId) {
        List<Vote> votes = repository.findByUserIdOrderByDateDesc(userId);
        if (votes.isEmpty()) {
            throw new NotFoundException("Votes didn't exist");
        }
        return votes.stream().map(vote -> new VoteHistoryTO(vote.getRestaurant().getId(), vote.getDate())).toList();
    }

    @Transactional
    public VoteTO save(User user, int restaurantId) {
        LocalDateTime now = timeProvider.dateTimeNow();
        MenuTO menu = menuService.getEnabled(restaurantId, now.toLocalDate());
        Restaurant restaurant = restaurantService.getReference(restaurantId);
        if (menu.getDishes().isEmpty()) {
            throw new NotFoundException("Cannot vote for restaurant without dishes in menu");
        }

        Vote vote = repository.findByUserIdAndDate(user.getId(), now.toLocalDate())
                .map(existing -> {
                    checkDeadline(now.toLocalTime());
                    existing.setRestaurant(restaurant);
                    return existing;
                })
                .orElseGet(() -> repository.save(new Vote(user, restaurant, now.toLocalDate())));

        return new VoteTO(vote.getRestaurant().getId());
    }

    private void checkDeadline(LocalTime time) {
        if (!time.isBefore(CHANGE_DEADLINE)) {
            throw new VoteTimeExpiredException("Cannot change vote after " + CHANGE_DEADLINE);
        }
    }
}
