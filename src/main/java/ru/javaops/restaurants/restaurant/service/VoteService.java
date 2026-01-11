package ru.javaops.restaurants.restaurant.service;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import ru.javaops.restaurants.common.error.NotFoundException;
import ru.javaops.restaurants.common.error.VoteTimeExpiredException;
import ru.javaops.restaurants.common.service.BaseService;
import ru.javaops.restaurants.common.time.TimeService;
import ru.javaops.restaurants.restaurant.model.Restaurant;
import ru.javaops.restaurants.restaurant.model.Vote;
import ru.javaops.restaurants.restaurant.projection.RestaurantVoteProjection;
import ru.javaops.restaurants.restaurant.repository.VoteRepository;
import ru.javaops.restaurants.restaurant.to.MenuTO;
import ru.javaops.restaurants.restaurant.to.RestaurantTO;
import ru.javaops.restaurants.restaurant.to.RestaurantVotesTO;
import ru.javaops.restaurants.restaurant.to.VoteTO;
import ru.javaops.restaurants.user.model.User;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static ru.javaops.restaurants.common.time.TimeService.CHANGE_DEADLINE;

@Service
public class VoteService extends BaseService<Vote, VoteRepository> {
    private final RestaurantService restaurantService;
    private final MenuService menuService;
    private final TimeService timeService;

    public VoteService(VoteRepository repository,
                       RestaurantService restaurantService,
                       TimeService timeService,
                       MenuService menuService) {
        super(repository);
        this.restaurantService = restaurantService;
        this.timeService = timeService;
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
        return new VoteTO(repository.findByUserIdAndDate(userId, date).map(vote -> vote.getRestaurant().getId())
                .orElseThrow(() -> new NotFoundException("Vote didn't exist")));
    }

    @Transactional
    public VoteTO save(User user, int restaurantId) {
        Restaurant restaurant = restaurantService.getReferenceEnabled(restaurantId);
        LocalDate today = LocalDate.now();

        MenuTO menu = menuService.getEnabled(restaurantId, today);
        if (menu.getDishes().isEmpty()) {
            throw new NotFoundException("Cannot vote for restaurant without dishes in menu");
        }

        Vote vote = repository.findByUserIdAndDate(user.getId(), today)
                .map(existing -> {
                    checkDeadline();
                    existing.setRestaurant(restaurant);
                    return existing;
                })
                .orElseGet(() -> repository.save(new Vote(user, restaurant, today)));

        return new VoteTO(vote.getRestaurant().getId());
    }

    private void checkDeadline() {
        if (!timeService.now().isBefore(CHANGE_DEADLINE)) {
            throw new VoteTimeExpiredException("Cannot change vote after " + CHANGE_DEADLINE);
        }
    }
}
