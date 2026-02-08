package com.github.dmvegel.restaurants.restaurant.to;

import com.github.dmvegel.restaurants.common.to.NamedTo;
import lombok.EqualsAndHashCode;
import lombok.Value;

import java.util.Set;
import java.util.stream.Collectors;

@Value
@EqualsAndHashCode(callSuper = true)
public class RestaurantWithMenuTO extends NamedTo {
    Set<MenuTO> menus;

    public RestaurantWithMenuTO(Integer id, String name, Set<MenuTO> menus) {
        super(id, name);
        this.menus = menus;
    }

    @Override
    public String toString() {
        return "RestaurantWithMenuTO{" +
                "id=" + getId() +
                ", name='" + getName() + '\'' +
                ", menus=\n" +
                menus.stream()
                        .map(MenuTO::toString)
                        .collect(Collectors.joining("\n")) +
                "\n}";
    }
}
