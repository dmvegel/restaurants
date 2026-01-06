package ru.javaops.bootjava.restaurant.service;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import ru.javaops.bootjava.common.error.NotFoundException;
import ru.javaops.bootjava.common.service.BaseService;
import ru.javaops.bootjava.restaurant.model.Restaurant;
import ru.javaops.bootjava.restaurant.repository.RestaurantRepository;
import ru.javaops.bootjava.restaurant.to.RestaurantTO;
import ru.javaops.bootjava.restaurant.to.RestaurantWithMenuTO;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

@Service
public class RestaurantService extends BaseService<Restaurant, RestaurantRepository> {
    private static final String NOT_FOUND_RESTAURANT = "Restaurant with id=%d not found";

    public RestaurantService(RestaurantRepository repository) {
        super(repository);
    }

    public List<RestaurantWithMenuTO> getAllWithMenus(LocalDate date) {
        return repository.getRestaurantsWithMenusByDate(date)
                .orElse(Collections.emptyList())
                .stream()
                .map(RestaurantWithMenuTO::new)
                .toList();
    }

    public RestaurantTO get(int id) {
        return new RestaurantTO(getExisted(id));
    }

    public Restaurant getReference(int id) {
        return repository.getReferenceById(id);
    }

    public List<RestaurantTO> getAll() {
        return repository.findAll().stream().map(RestaurantTO::new).toList();
    }

//    public Restaurant getWithMenus(int id) {
//        return repository.getRestaurantWithAllMenus(id).orElseThrow(
//                () -> new NotFoundException(String.format(NOT_FOUND_RESTAURANT, id)));
//    }

    public RestaurantWithMenuTO getWithMenu(int id, LocalDate date) {
        return repository.getRestaurantWithMenu(id, date).map(RestaurantWithMenuTO::new).orElseThrow(
                () -> new NotFoundException("Menu with restaurantId=" + id + " for date=" + date + " not found"));
    }

    @Transactional
    public Restaurant create(RestaurantTO restaurantTo) {
        return repository.save(new Restaurant(restaurantTo));
    }

    @Transactional
    public Restaurant update(RestaurantTO restaurantTo) {
        Restaurant restaurant = repository.findById(restaurantTo.getId()).orElseThrow(
                () -> new NotFoundException(String.format(NOT_FOUND_RESTAURANT, restaurantTo.getId())));
        restaurant.setName(restaurantTo.getName());
        return restaurant;
    }
}
