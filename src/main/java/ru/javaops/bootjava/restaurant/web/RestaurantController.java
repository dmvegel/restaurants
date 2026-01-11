package ru.javaops.bootjava.restaurant.web;

import io.swagger.v3.oas.annotations.Parameter;
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
    public RestaurantTO getEnabled(@PathVariable int id) {
        log.info("get enabled restaurant with id={}", id);
        return restaurantService.getEnabled(id);
    }

    @GetMapping
    public List<RestaurantTO> getAllEnabled() {
        log.info("get all enabled restaurants");
        return restaurantService.getAllEnabled();
    }

    @GetMapping("/with-menus")
    public List<RestaurantWithMenuTO> getAllEnabledWithMenu(
            @Parameter(description = "Defaults to today if not provided")
            @RequestParam(required = false) LocalDate date) {
        log.info("get all restaurants with menus on date={}", date);
        return restaurantService.getAllEnabledWithMenu(date);
    }
}
