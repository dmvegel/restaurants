package ru.javaops.bootjava.restaurant.web;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.javaops.bootjava.app.config.WebConfig;
import ru.javaops.bootjava.restaurant.service.MenuService;
import ru.javaops.bootjava.restaurant.to.MenuTO;

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
        return menuService.get(restaurantId, date);
    }

    @GetMapping
    public List<MenuTO> getAll(@PathVariable int restaurantId) {
        log.info("getAll");
        return menuService.getAll(restaurantId);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<MenuTO> create(@PathVariable int restaurantId, @Valid @RequestBody MenuTO menuTo) {
        log.info("create menu {} for restaurantId={}", menuTo, restaurantId);
        MenuTO created = menuService.create(menuTo, restaurantId);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{date}")
                .buildAndExpand(restaurantId, menuTo.getDate())
                .toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@PathVariable int restaurantId, @Valid @RequestBody MenuTO menuTo) {
        log.info("update {} with restaurantId={} and date={}", menuTo, restaurantId, menuTo.getDate());
        menuService.update(menuTo, restaurantId);
    }
}
