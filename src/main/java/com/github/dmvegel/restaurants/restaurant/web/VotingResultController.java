package com.github.dmvegel.restaurants.restaurant.web;

import com.github.dmvegel.restaurants.app.config.WebConfig;
import com.github.dmvegel.restaurants.restaurant.service.VoteService;
import com.github.dmvegel.restaurants.restaurant.to.RestaurantVotesTO;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping(value = VotingResultController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE, version = WebConfig.CURRENT_VERSION)
@AllArgsConstructor
@Slf4j
public class VotingResultController {
    static final String REST_URL = "/api/voting-results";
    private final VoteService voteService;

    @GetMapping(value = "{date}")
    public List<RestaurantVotesTO> get(@PathVariable LocalDate date, @RequestParam(required = false) Integer restaurantId) {
        if (restaurantId == null) {
            log.info("get all restaurants with votes on date={}", date);
            return voteService.getRestaurantsWithVotes(date);
        }
        log.info("get restaurant with votes for restaurantId={} on date={}", restaurantId, date);
        return List.of(voteService.getRestaurantWithVotes(restaurantId, date));
    }
}
