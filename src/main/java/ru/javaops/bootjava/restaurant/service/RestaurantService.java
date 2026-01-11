package ru.javaops.bootjava.restaurant.service;

import jakarta.transaction.Transactional;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import ru.javaops.bootjava.common.error.NotFoundException;
import ru.javaops.bootjava.common.service.BaseService;
import ru.javaops.bootjava.restaurant.model.Restaurant;
import ru.javaops.bootjava.restaurant.repository.RestaurantRepository;
import ru.javaops.bootjava.restaurant.to.AdminRestaurantTO;
import ru.javaops.bootjava.restaurant.to.RestaurantTO;
import ru.javaops.bootjava.restaurant.to.RestaurantWithMenuTO;
import ru.javaops.bootjava.restaurant.util.RestaurantUtil;

import java.time.LocalDate;
import java.util.List;

@Service
public class RestaurantService extends BaseService<Restaurant, RestaurantRepository> {
    private static final String NOT_FOUND_RESTAURANT = "Restaurant with id=%d not found";

    public RestaurantService(RestaurantRepository repository) {
        super(repository);
    }

    public AdminRestaurantTO get(int id) {
        Restaurant restaurant = getExisted(id);
        return RestaurantUtil.getAdminTo(restaurant);
    }

    public Restaurant getReference(int id) {
        return repository.getReferenceById(id);
    }

    public Restaurant getReferenceEnabled(int id) {
        return repository.getReferenceEnabled(id)
                .orElseThrow(() -> new NotFoundException(String.format(NOT_FOUND_RESTAURANT, id)));
    }

    public List<AdminRestaurantTO> getAll() {
        return repository.findAll().stream()
                .map(RestaurantUtil::getAdminTo).toList();
    }

    @Cacheable("restaurantById")
    public RestaurantTO getEnabled(int id) {
        return RestaurantUtil.getTo(repository.getEnabledById(id)
                .orElseThrow(() -> new NotFoundException(String.format(NOT_FOUND_RESTAURANT, id))));
    }

    @Cacheable("restaurants")
    public List<RestaurantTO> getAllEnabled() {
        return repository.getAllEnabled().stream().map(RestaurantUtil::getTo).toList();
    }

    public List<RestaurantWithMenuTO> getAllEnabledWithMenu(LocalDate date) {
        LocalDate actualDate = date != null ? date : LocalDate.now();
        return repository.getEnabledWithMenusByDate(actualDate).stream().map(RestaurantUtil::getWithMenuTo).toList();
    }

    @CacheEvict(value = {"restaurantById", "restaurants"}, allEntries = true)
    @Transactional
    public AdminRestaurantTO create(RestaurantTO restaurantTo) {
        Restaurant restaurant = repository.save(new Restaurant(restaurantTo.getName()));
        return RestaurantUtil.getAdminTo(restaurant);
    }

    @CacheEvict(value = {"restaurantById", "restaurants"}, allEntries = true)
    @Transactional
    public Restaurant update(RestaurantTO restaurantTo) {
        Restaurant restaurant = getExisted(restaurantTo.getId());
        restaurant.setName(restaurantTo.getName());
        return restaurant;
    }

    @CacheEvict(value = {"restaurantById", "restaurants"}, allEntries = true)
    @Transactional
    public void enable(int id, boolean enabled) {
        Restaurant restaurant = getExisted(id);
        restaurant.setEnabled(enabled);
    }
}
