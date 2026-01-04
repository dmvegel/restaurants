package ru.javaops.bootjava.restaurant.service;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import ru.javaops.bootjava.common.error.NotFoundException;
import ru.javaops.bootjava.common.service.BaseService;
import ru.javaops.bootjava.restaurant.model.Menu;
import ru.javaops.bootjava.restaurant.model.Restaurant;
import ru.javaops.bootjava.restaurant.repository.MenuRepository;

import java.time.LocalDate;
import java.util.List;

@Service
public class MenuService extends BaseService<Menu, MenuRepository> {
    private final RestaurantService restaurantService;

    public MenuService(MenuRepository repository, RestaurantService restaurantService) {
        super(repository);
        this.restaurantService = restaurantService;
    }

    public List<Menu> getAll(int restaurantId) {
        return repository.findByRestaurantIdOrderByDateDesc(restaurantId);
    }

    public Menu get(int id) {
        return getExisted(id);
    }

    public Menu get(int restaurantId, LocalDate date) {
        return repository.findByRestaurantIdAndDate(restaurantId, date).orElseThrow(
                () -> new NotFoundException("Menu with restaurantId=" + restaurantId + " and date =" + date + " not found"));
    }

    @Transactional
    public Menu save(Menu menu, int restaurantId) {
        if (menu.isNew()) {
            Restaurant restaurant = restaurantService.get(restaurantId);
            menu.setRestaurant(restaurant);
            return repository.save(menu);
        }
        Menu saved = getExisted(menu.getId());
        saved.setDishes(menu.getDishes());
        return saved;
    }

    public void delete(int id) {
        deleteExisted(id);
    }
}
