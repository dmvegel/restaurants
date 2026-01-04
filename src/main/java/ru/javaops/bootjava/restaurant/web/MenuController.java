package ru.javaops.bootjava.restaurant.web;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.javaops.bootjava.app.config.WebConfig;
import ru.javaops.bootjava.restaurant.model.Menu;
import ru.javaops.bootjava.restaurant.service.MenuService;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping(value = MenuController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE, version = WebConfig.CURRENT_VERSION)
@AllArgsConstructor
@Slf4j
public class MenuController {
    static final String REST_URL = "/api/restaurants/{restaurantId}/menus";
    private final MenuService menuService;

    @GetMapping("/{date}")
    public Menu get(@PathVariable int restaurantId, @PathVariable LocalDate date) {
        return menuService.get(restaurantId, date);
    }

    @GetMapping
    public List<Menu> getAll(@PathVariable int restaurantId) {
        log.info("getAll");
        return menuService.getAll(restaurantId);
    }
}
