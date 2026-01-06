package ru.javaops.bootjava.restaurant.web;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ru.javaops.bootjava.app.config.WebConfig;
import ru.javaops.bootjava.restaurant.service.RestaurantService;
import ru.javaops.bootjava.restaurant.to.RestaurantTO;
import ru.javaops.bootjava.restaurant.to.RestaurantWithMenuTO;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping(value = RestaurantController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE, version = WebConfig.CURRENT_VERSION)
@AllArgsConstructor
@Slf4j
public class RestaurantController {
    static final String REST_URL = "/api/restaurants";
    private final RestaurantService restaurantService;

    @GetMapping("/{id}")
    public RestaurantTO get(@PathVariable int id) {
        return restaurantService.get(id);
    }

    @GetMapping("/{id}/with-menu")
    public RestaurantWithMenuTO get(@PathVariable int id, @RequestParam LocalDate date) {
        return restaurantService.getWithMenu(id, date);
    }

    @GetMapping
    public List<RestaurantTO> getAll() {
        log.info("getAll");
        return restaurantService.getAll();
    }

    @GetMapping("/with-menus")
    public List<RestaurantWithMenuTO> getAllWithMenus(@RequestParam LocalDate date) {
        log.info("getAllWithMenus");
        return restaurantService.getAllWithMenus(date != null ? date : LocalDate.now());
    }
}
