package ru.javaops.restaurants.restaurant.to;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.EqualsAndHashCode;
import lombok.Value;
import ru.javaops.restaurants.common.to.NamedTo;

@Value
@EqualsAndHashCode(callSuper = true)
public class AdminRestaurantTO extends NamedTo {
    @JsonCreator
    public AdminRestaurantTO(Integer id, String name, boolean enabled) {
        super(id, name);
        this.enabled = enabled;
    }

    boolean enabled;
}
