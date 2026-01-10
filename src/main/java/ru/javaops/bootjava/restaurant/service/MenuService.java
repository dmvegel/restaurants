package ru.javaops.bootjava.restaurant.service;

import jakarta.transaction.Transactional;
import jakarta.validation.constraints.NotNull;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import ru.javaops.bootjava.common.error.NotFoundException;
import ru.javaops.bootjava.common.service.BaseService;
import ru.javaops.bootjava.restaurant.model.Menu;
import ru.javaops.bootjava.restaurant.repository.MenuRepository;
import ru.javaops.bootjava.restaurant.to.MenuTO;
import ru.javaops.bootjava.restaurant.util.DishUtil;
import ru.javaops.bootjava.restaurant.util.MenuUtil;

import java.time.LocalDate;
import java.util.List;

import static ru.javaops.bootjava.common.validation.ValidationUtil.assureIdConsistent;

@Service
public class MenuService extends BaseService<Menu, MenuRepository> {
    private static final String NOT_FOUND_RESTAURANT = "Menu with restaurantId=%d and date=%s not found";

    private final RestaurantService restaurantService;

    public MenuService(MenuRepository repository, RestaurantService restaurantService) {
        super(repository);
        this.restaurantService = restaurantService;
    }

    @Cacheable("menusByRestaurant")
    public List<MenuTO> getAllEnabled(int restaurantId) {
        List<Menu> menus = repository.findByRestaurantIdOrderByDateDescEnabled(restaurantId);
        return MenuUtil.getListTo(menus);
    }

    public List<MenuTO> getAll(int restaurantId) {
        List<Menu> menus = repository.findByRestaurantIdOrderByDateDesc(restaurantId);
        return MenuUtil.getListTo(menus);
    }

    @Cacheable("menuByRestaurantAndDate")
    public MenuTO getEnabled(int restaurantId, LocalDate date) {
        return MenuUtil.getTo(getByEnabledRestaurantIdAndDate(restaurantId, date));
    }

    public MenuTO get(int restaurantId, LocalDate date) {
        return MenuUtil.getTo(getByRestaurantIdAndDate(restaurantId, date));
    }

    private Menu getByEnabledRestaurantIdAndDate(int restaurantId, LocalDate date) {
        return repository.findByRestaurantIdAndDateEnabled(restaurantId, date).orElseThrow(
                () -> new NotFoundException(String.format(NOT_FOUND_RESTAURANT, restaurantId, date)));
    }

    @CacheEvict(value = {"menusByRestaurant", "menuByRestaurantAndDate"}, allEntries = true)
    @Transactional
    public MenuTO create(MenuTO menuTo, int restaurantId) {
        Menu menu = MenuUtil.getFromTo(menuTo, restaurantService.getReference(restaurantId));
        return MenuUtil.getTo(repository.save(menu));
    }

    @CacheEvict(value = {"menusByRestaurant", "menuByRestaurantAndDate"}, allEntries = true)
    @Transactional
    public void update(MenuTO menuTo, int restaurantId) {
        Menu saved = getByRestaurantIdAndDate(restaurantId, menuTo.getDate());
        assureIdConsistent(menuTo, saved.getId());
        saved.getDishes().clear();
        saved.getDishes().addAll(DishUtil.getListFromTo(menuTo.getDishes()));
    }

    private Menu getByRestaurantIdAndDate(int restaurantId, @NotNull LocalDate date) {
        return repository.findByRestaurantIdAndDate(restaurantId, date).orElseThrow(
                () -> new NotFoundException(String.format(NOT_FOUND_RESTAURANT, restaurantId, date)));
    }
}
