package ru.javaops.bootjava.restaurant.service;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import ru.javaops.bootjava.common.error.NotFoundException;
import ru.javaops.bootjava.common.service.BaseService;
import ru.javaops.bootjava.restaurant.model.Dish;
import ru.javaops.bootjava.restaurant.model.Menu;
import ru.javaops.bootjava.restaurant.repository.MenuRepository;
import ru.javaops.bootjava.restaurant.to.MenuTO;

import java.time.LocalDate;
import java.util.List;

@Service
public class MenuService extends BaseService<Menu, MenuRepository> {
    private final RestaurantService restaurantService;

    public MenuService(MenuRepository repository, RestaurantService restaurantService) {
        super(repository);
        this.restaurantService = restaurantService;
    }

    public List<MenuTO> getAll(int restaurantId) {
        List<Menu> menus = repository.findByRestaurantIdOrderByDateDesc(restaurantId);
        if (menus.isEmpty()) {
            throw new NotFoundException("Restaurant with id=" + restaurantId + " not found");
        }
        return menus.stream().map(MenuTO::new).toList();
    }

    public Menu get(int id) {
        return getExisted(id);
    }

    public MenuTO get(int restaurantId, LocalDate date) {
        return new MenuTO(getByIdAndDate(restaurantId, date));
    }

    private Menu getByIdAndDate(int restaurantId, LocalDate date) {
        return repository.findByRestaurantIdAndDate(restaurantId, date).orElseThrow(
                () -> new NotFoundException("Menu with restaurantId=" + restaurantId + " and date =" + date + " not found"));
    }

    @Transactional
    public MenuTO create(MenuTO menuTo, int restaurantId) {
        Menu menu = new Menu(menuTo, restaurantService.getReference(restaurantId));
        return new MenuTO(repository.save(menu));
    }

    @Transactional
    public Menu update(MenuTO menuTo, int restaurantId) {
        Menu saved = getByIdAndDate(restaurantId, menuTo.getDate());
        saved.getDishes().clear();
        menuTo.getDishes().stream()
                .map(Dish::new)
                .forEach(dish -> {
                    dish.setMenu(saved);
                    saved.getDishes().add(dish);
                });
//        saved.setDishes(menuTo.getDishes().stream().map(Dish::new).collect(Collectors.toSet()));
//        saved.getDishes().forEach(dish -> dish.setMenu(saved));
        return saved;
    }
}
