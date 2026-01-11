package ru.javaops.restaurants.restaurant.web;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import ru.javaops.restaurants.app.AuthUser;
import ru.javaops.restaurants.app.config.WebConfig;
import ru.javaops.restaurants.restaurant.service.VoteService;
import ru.javaops.restaurants.restaurant.to.RestaurantVotesTO;
import ru.javaops.restaurants.restaurant.to.VoteTO;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping(value = VoteController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE, version = WebConfig.CURRENT_VERSION)
@AllArgsConstructor
@Slf4j
public class VoteController {
    static final String REST_URL = "/api/votings";
    private final VoteService voteService;

    @PostMapping(value = "/restaurants/{restaurantId}")
    public VoteTO vote(@AuthenticationPrincipal AuthUser authUser, @PathVariable int restaurantId) {
        log.info("user id={} vote for restaurantId {}", authUser.id(), restaurantId);
        return voteService.save(authUser.getUser(), restaurantId);
    }

    @GetMapping("/{date}")
    public List<RestaurantVotesTO> getRestaurantsWithVotes(@PathVariable LocalDate date) {
        log.info("get all restaurants with votes on date={}", date);
        return voteService.getRestaurantsWithVotes(date);
    }

    @GetMapping("{date}/restaurants/{restaurantId}")
    public RestaurantVotesTO get(@PathVariable LocalDate date, @PathVariable int restaurantId) {
        log.info("get restaurant with votes for restaurantId={} on date={}", restaurantId, date);
        return voteService.getRestaurantWithVotes(restaurantId, date);
    }

    @GetMapping("{date}/me")
    public VoteTO getUserVote(@AuthenticationPrincipal AuthUser authUser, @PathVariable LocalDate date) {
        log.info("get vote for userId={} for date={}", authUser.id(), date);
        return voteService.getByUserIdAndDate(authUser.id(), date);
    }
}
