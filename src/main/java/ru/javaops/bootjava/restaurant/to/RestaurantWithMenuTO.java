package ru.javaops.bootjava.restaurant.to;

import lombok.EqualsAndHashCode;
import lombok.Value;
import ru.javaops.bootjava.common.to.NamedTo;
import ru.javaops.bootjava.restaurant.model.Restaurant;

import java.util.Set;
import java.util.stream.Collectors;

@Value
@EqualsAndHashCode(callSuper = true)
public class RestaurantWithMenuTO extends NamedTo {
    Set<MenuTO> menus;
//    public RestaurantWithMenuTO(Integer id, String name, Set<MenuTO> menus) {
//        super(id, name);
//        this.menus = menus;
//    }

    public RestaurantWithMenuTO(Restaurant restaurant) {
        super(restaurant.getId(), restaurant.getName());
        this.menus = restaurant.getMenus().stream().map(MenuTO::new).collect(Collectors.toSet());
    }
}
