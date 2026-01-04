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
import ru.javaops.bootjava.restaurant.model.Menu;
import ru.javaops.bootjava.restaurant.service.MenuService;

import java.net.URI;
import java.time.LocalDate;

import static ru.javaops.bootjava.common.validation.ValidationUtil.checkNew;

@RestController
@RequestMapping(value = AdminMenuController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE, version = WebConfig.CURRENT_VERSION)
@AllArgsConstructor
@Slf4j
public class AdminMenuController {
    static final String REST_URL = "/api/admin/restaurants/{restaurantId}/menus";
    private final MenuService menuService;

    @DeleteMapping("/{date}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int restaurantId, @PathVariable LocalDate date) {
        menuService.delete(restaurantId, date);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Menu> create(@PathVariable int restaurantId, @Valid @RequestBody Menu menu) {
        log.info("create {}", menu);
        checkNew(menu);
        Menu created = menuService.save(menu, restaurantId);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{date}")
                .buildAndExpand(menu.getDate())
                .toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@PathVariable int restaurantId, @Valid @RequestBody Menu menu) {
        log.info("update {} with restaurantId={} and date={}", menu, restaurantId, menu.getDate());
        menuService.save(menu, restaurantId);
    }
}
