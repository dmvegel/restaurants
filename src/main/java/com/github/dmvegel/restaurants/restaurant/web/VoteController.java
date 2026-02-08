package com.github.dmvegel.restaurants.restaurant.web;

import com.github.dmvegel.restaurants.app.AuthUser;
import com.github.dmvegel.restaurants.app.config.WebConfig;
import com.github.dmvegel.restaurants.common.time.TimeProvider;
import com.github.dmvegel.restaurants.restaurant.service.VoteService;
import com.github.dmvegel.restaurants.restaurant.to.VoteHistoryTO;
import com.github.dmvegel.restaurants.restaurant.to.VoteTO;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping(value = VoteController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE, version = WebConfig.CURRENT_VERSION)
@AllArgsConstructor
@Slf4j
public class VoteController {
    static final String REST_URL = "/api/votings";

    private final VoteService voteService;
    private final TimeProvider timeProvider;

    @PostMapping(value = "/restaurants", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<VoteTO> vote(@AuthenticationPrincipal AuthUser authUser, @Valid @RequestBody VoteTO vote) {
        log.info("user id={} vote for restaurantId {}", authUser.id(), vote.restaurantId());
        VoteTO created = voteService.save(authUser.getUser(), vote.restaurantId());
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{date}")
                .buildAndExpand(timeProvider.dateNow()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @PutMapping(value = "/restaurants", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public VoteTO revote(@AuthenticationPrincipal AuthUser authUser, @Valid @RequestBody VoteTO vote) {
        log.info("user id={} revote for restaurantId {}", authUser.id(), vote.restaurantId());
        return voteService.save(authUser.getUser(), vote.restaurantId());
    }

    @GetMapping("{date}")
    public VoteTO getUserVote(@AuthenticationPrincipal AuthUser authUser, @PathVariable LocalDate date) {
        log.info("get vote for userId={} for date={}", authUser.id(), date);
        return voteService.getByUserIdAndDate(authUser.id(), date);
    }

    @GetMapping
    public List<VoteHistoryTO> getUserVotes(@AuthenticationPrincipal AuthUser authUser) {
        log.info("get all votes for userId={}", authUser.id());
        return voteService.getByUserId(authUser.id());
    }
}
