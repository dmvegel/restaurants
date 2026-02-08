package com.github.dmvegel.restaurants.restaurant.service;

import com.github.dmvegel.restaurants.common.service.BaseService;
import com.github.dmvegel.restaurants.common.time.TimeProvider;
import com.github.dmvegel.restaurants.restaurant.model.Restaurant;
import com.github.dmvegel.restaurants.restaurant.repository.MenuRepository;
import com.github.dmvegel.restaurants.restaurant.repository.RestaurantRepository;
import com.github.dmvegel.restaurants.restaurant.to.AdminRestaurantTO;
import com.github.dmvegel.restaurants.restaurant.to.RestaurantTO;
import com.github.dmvegel.restaurants.restaurant.to.RestaurantWithMenuTO;
import com.github.dmvegel.restaurants.restaurant.util.RestaurantUtil;
import jakarta.transaction.Transactional;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class RestaurantService extends BaseService<Restaurant, RestaurantRepository> {
    private static final String NOT_FOUND_RESTAURANT = "Restaurant with id=%d not found";

    private final MenuRepository menuRepository;
    private final TimeProvider timeProvider;

    public RestaurantService(RestaurantRepository repository, MenuRepository menuRepository, TimeProvider timeProvider) {
        super(repository);
        this.menuRepository = menuRepository;
        this.timeProvider = timeProvider;
    }

    public AdminRestaurantTO get(int id) {
        Restaurant restaurant = getExisted(id);
        return RestaurantUtil.getAdminTo(restaurant);
    }

    public Restaurant getReference(int id) {
        return repository.getReferenceById(id);
    }

    public Restaurant getReferenceEnabled(int id) {
        return getOrNotFound(() -> repository.getEnabledById(id), String.format(NOT_FOUND_RESTAURANT, id));
    }

    public List<AdminRestaurantTO> getAll() {
        return repository.findAll().stream()
                .map(RestaurantUtil::getAdminTo).toList();
    }

    @Cacheable("restaurantById")
    public RestaurantTO getEnabled(int id) {
        return RestaurantUtil.getTo(getOrNotFound(() -> repository.getEnabledById(id),
                String.format(NOT_FOUND_RESTAURANT, id)));
    }

    @Cacheable("restaurants")
    public List<RestaurantTO> getAllEnabled() {
        return repository.getAllEnabled().stream().map(RestaurantUtil::getTo).toList();
    }

    public List<RestaurantWithMenuTO> getAllEnabledWithMenu(LocalDate date) {
        LocalDate actualDate = date != null ? date : timeProvider.dateNow();
        return repository.getEnabledWithMenusByDate(actualDate).stream().map(RestaurantUtil::getWithMenuTo).toList();
    }

    @CacheEvict(value = "restaurants", allEntries = true)
    @Transactional
    public AdminRestaurantTO create(RestaurantTO restaurantTo) {
        Restaurant restaurant = repository.save(new Restaurant(restaurantTo.getName()));
        return RestaurantUtil.getAdminTo(restaurant);
    }

    @Caching(evict = {
            @CacheEvict(value = "restaurants", allEntries = true),
            @CacheEvict(value = "restaurantById", key = "#restaurantTo.id")
    })
    @Transactional
    public Restaurant update(RestaurantTO restaurantTo) {
        Restaurant restaurant = getExisted(restaurantTo.getId());
        restaurant.setName(restaurantTo.getName());
        return restaurant;
    }

    @Caching(evict = {
            @CacheEvict(value = "restaurants", allEntries = true),
            @CacheEvict(value = "restaurantById", key = "#id"),
            @CacheEvict(value = "menusByRestaurant", key = "#id"),
            @CacheEvict(value = "menuByRestaurantAndDate", allEntries = true)
    })
    @Transactional
    public void enable(int id, boolean enabled) {
        Restaurant restaurant = getExisted(id);
        restaurant.setEnabled(enabled);
        menuRepository.findByRestaurantIdOrderByDateDesc(id).forEach(menu -> menu.setEnabled(enabled));
    }

    @Caching(evict = {
            @CacheEvict(value = "restaurants", allEntries = true),
            @CacheEvict(value = "restaurantById", key = "#id"),
            @CacheEvict(value = "menusByRestaurant", key = "#id"),
            @CacheEvict(value = "menuByRestaurantAndDate", allEntries = true)
    })
    @Transactional
    public void delete(int id) {
        repository.delete(getExisted(id));
    }
}
