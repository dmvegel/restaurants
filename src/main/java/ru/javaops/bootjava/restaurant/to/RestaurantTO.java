package ru.javaops.bootjava.restaurant.to;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.EqualsAndHashCode;
import lombok.Value;
import ru.javaops.bootjava.common.to.NamedTo;
import ru.javaops.bootjava.restaurant.model.Restaurant;

@Value
@EqualsAndHashCode(callSuper = true)
public class RestaurantTO extends NamedTo {
    @JsonCreator
    public RestaurantTO(Integer id, String name) {
        super(id, name);
    }

    public RestaurantTO(Restaurant restaurant) {
        super(restaurant.getId(), restaurant.getName());
    }
}
