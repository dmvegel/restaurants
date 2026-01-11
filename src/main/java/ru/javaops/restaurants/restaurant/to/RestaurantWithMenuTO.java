package ru.javaops.restaurants.restaurant.to;

import lombok.EqualsAndHashCode;
import lombok.Value;
import ru.javaops.restaurants.common.to.NamedTo;

import java.util.Set;

@Value
@EqualsAndHashCode(callSuper = true)
public class RestaurantWithMenuTO extends NamedTo {
    Set<MenuTO> menus;

    public RestaurantWithMenuTO(Integer id, String name, Set<MenuTO> menus) {
        super(id, name);
        this.menus = menus;
    }
}
