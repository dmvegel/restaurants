package ru.javaops.bootjava.restaurant.service;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.javaops.bootjava.common.error.NotFoundException;
import ru.javaops.bootjava.common.service.BaseService;
import ru.javaops.bootjava.restaurant.model.Restaurant;
import ru.javaops.bootjava.restaurant.repository.RestaurantRepository;

import java.time.LocalDate;
import java.util.List;

@Service
public class RestaurantService extends BaseService<Restaurant, RestaurantRepository> {
    private static final String NOT_FOUND_RESTAURANT = "Restaurant with id=%d not found";

    public RestaurantService(RestaurantRepository repository) {
        super(repository);
    }

    public List<Restaurant> getAll() {
        return repository.findAll(Sort.by(Sort.Direction.ASC, "name"));
    }

    public Restaurant get(int id) {
        return getExisted(id);
    }

    public Restaurant getWithMenus(int id) {
        return repository.getRestaurantWithAllMenusAndDishes(id).orElseThrow(
                () -> new NotFoundException(String.format(NOT_FOUND_RESTAURANT, id)));
    }

    public Restaurant getWithMenu(int id, LocalDate date) {
        return repository.getRestaurantWithMenuAndDishes(id, date).orElseThrow(
                () -> new NotFoundException(String.format(NOT_FOUND_RESTAURANT, id)));
    }

    public Restaurant save(Restaurant restaurant) {
        return repository.save(restaurant);
    }

    public void delete(int id) {
        deleteExisted(id);
    }
}
