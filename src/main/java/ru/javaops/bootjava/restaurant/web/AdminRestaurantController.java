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
import ru.javaops.bootjava.restaurant.service.RestaurantService;
import ru.javaops.bootjava.restaurant.to.AdminRestaurantTO;
import ru.javaops.bootjava.restaurant.to.RestaurantTO;

import java.net.URI;
import java.util.List;

import static ru.javaops.bootjava.common.validation.ValidationUtil.assureIdConsistent;
import static ru.javaops.bootjava.common.validation.ValidationUtil.checkNew;

@RestController
@RequestMapping(value = AdminRestaurantController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE, version = WebConfig.CURRENT_VERSION)
@AllArgsConstructor
@Slf4j
public class AdminRestaurantController {
    static final String REST_URL = "/api/admin/restaurants";
    private final RestaurantService restaurantService;

    @GetMapping("/{id}")
    public AdminRestaurantTO get(@PathVariable int id) {
        return restaurantService.get(id);
    }

    @GetMapping
    public List<AdminRestaurantTO> getAll() {
        log.info("getAll");
        return restaurantService.getAll();
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AdminRestaurantTO> create(@Valid @RequestBody RestaurantTO restaurantTo) {
        log.info("create {}", restaurantTo);
        checkNew(restaurantTo);
        AdminRestaurantTO created = restaurantService.create(restaurantTo);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(created.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@Valid @RequestBody RestaurantTO restaurantTo, @PathVariable int id) {
        log.info("update {} with id={}", restaurantTo, id);
        assureIdConsistent(restaurantTo, id);
        restaurantService.update(restaurantTo);
    }

    @PatchMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void enable(@PathVariable int id, @RequestParam boolean enabled) {
        log.info(enabled ? "enable restaurant {}" : "disable restaurant {}", id);
        restaurantService.enable(id, enabled);
    }
}
