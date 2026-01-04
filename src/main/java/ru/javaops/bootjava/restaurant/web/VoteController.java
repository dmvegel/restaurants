package ru.javaops.bootjava.restaurant.web;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.javaops.bootjava.app.AuthUser;
import ru.javaops.bootjava.app.config.WebConfig;
import ru.javaops.bootjava.restaurant.service.VoteService;

@RestController
@RequestMapping(value = VoteController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE, version = WebConfig.CURRENT_VERSION)
@AllArgsConstructor
@Slf4j
public class VoteController {
    static final String REST_URL = "/api/restaurants/{restaurantId}/votes";
    private final VoteService voteService;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public void vote(@AuthenticationPrincipal AuthUser authUser, @PathVariable int restaurantId) {
        log.info("user id={} vote for restaurantId {}", authUser.id(), restaurantId);
        voteService.save(authUser.getUser(), restaurantId);
    }
}
