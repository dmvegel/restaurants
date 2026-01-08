package ru.javaops.bootjava.restaurant.to;

import lombok.EqualsAndHashCode;
import lombok.Value;
import ru.javaops.bootjava.common.to.NamedTo;

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
