package ru.javaops.bootjava.restaurant.web;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.javaops.bootjava.app.config.WebConfig;
import ru.javaops.bootjava.restaurant.service.MenuService;
import ru.javaops.bootjava.restaurant.to.MenuTO;

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
    public MenuTO getEnabled(@PathVariable int restaurantId, @PathVariable LocalDate date) {
        return menuService.getEnabled(restaurantId, date);
    }

    @GetMapping
    public List<MenuTO> getAllEnabled(@PathVariable int restaurantId) {
        log.info("getAll");
        return menuService.getAllEnabled(restaurantId);
    }
}
