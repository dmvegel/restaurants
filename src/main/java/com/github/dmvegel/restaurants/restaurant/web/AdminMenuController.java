package com.github.dmvegel.restaurants.restaurant.web;

import com.github.dmvegel.restaurants.app.config.WebConfig;
import com.github.dmvegel.restaurants.restaurant.service.MenuService;
import com.github.dmvegel.restaurants.restaurant.to.MenuTO;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping(value = AdminMenuController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE, version = WebConfig.CURRENT_VERSION)
@AllArgsConstructor
@Slf4j
public class AdminMenuController {
    static final String REST_URL = "/api/admin/restaurants/{restaurantId}/menus";
    private final MenuService menuService;

    @GetMapping("/{date}")
    public MenuTO get(@PathVariable int restaurantId, @PathVariable LocalDate date) {
        log.info("get restaurantId={} on date={}", restaurantId, date);
        return menuService.get(restaurantId, date);
    }

    @GetMapping
    public List<MenuTO> getAll(@PathVariable int restaurantId) {
        log.info("get all menus for restaurantId={}", restaurantId);
        return menuService.getAll(restaurantId);
    }

    @PostMapping(value = "/{date}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<MenuTO> create(@Valid @RequestBody MenuTO menuTo,
                                         @PathVariable int restaurantId,
                                         @PathVariable LocalDate date) {
        log.info("create menu {} for restaurantId={}", menuTo, restaurantId);
        MenuTO created = menuService.create(menuTo, restaurantId, date);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{date}")
                .buildAndExpand(restaurantId, date)
                .toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @PutMapping(value = "/{date}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@Valid @RequestBody MenuTO menuTo, @PathVariable int restaurantId, @PathVariable LocalDate date) {
        log.info("update {} with restaurantId={} and date={}", menuTo, restaurantId, date);
        menuService.update(menuTo, restaurantId, date);
    }

    @DeleteMapping("/{date}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int restaurantId, @PathVariable LocalDate date) {
        log.info("delete with restaurantId={} and date={}", restaurantId, date);
        menuService.delete(restaurantId, date);
    }
}
