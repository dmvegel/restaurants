package com.github.dmvegel.restaurants.user.web;

import com.github.dmvegel.restaurants.app.AuthUser;
import com.github.dmvegel.restaurants.app.config.WebConfig;
import com.github.dmvegel.restaurants.user.UsersUtil;
import com.github.dmvegel.restaurants.user.model.User;
import com.github.dmvegel.restaurants.user.service.UserService;
import com.github.dmvegel.restaurants.user.to.UserTo;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

import static com.github.dmvegel.restaurants.common.validation.ValidationUtil.assureIdConsistent;
import static com.github.dmvegel.restaurants.common.validation.ValidationUtil.checkNew;

@RestController
@RequestMapping(value = ProfileController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE, version = WebConfig.CURRENT_VERSION)
@Slf4j
public class ProfileController extends AbstractUserController {
    static final String REST_URL = "/api/profile";

    public ProfileController(UserService userService, UniqueMailValidator emailValidator) {
        super(userService, emailValidator);
    }

    @GetMapping
    public User get(@AuthenticationPrincipal AuthUser authUser) {
        log.info("get {}", authUser);
        return authUser.getUser();
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@AuthenticationPrincipal AuthUser authUser) {
        super.delete(authUser.id());
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<User> register(@Valid @RequestBody UserTo userTo) {
        log.info("register {}", userTo);
        checkNew(userTo);
        User created = userService.create(UsersUtil.createNewFromTo(userTo));
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL).build().toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@RequestBody @Valid UserTo userTo, @AuthenticationPrincipal AuthUser authUser) {
        log.info("update {} with id={}", userTo, authUser.id());
        assureIdConsistent(userTo, authUser.id());
        User user = new User(authUser.getUser());
        super.update(UsersUtil.updateFromTo(user, userTo));
    }
}