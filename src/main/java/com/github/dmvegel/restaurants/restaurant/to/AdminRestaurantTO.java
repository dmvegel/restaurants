package com.github.dmvegel.restaurants.restaurant.to;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.github.dmvegel.restaurants.common.to.NamedTo;
import lombok.EqualsAndHashCode;
import lombok.Value;

@Value
@EqualsAndHashCode(callSuper = true)
public class AdminRestaurantTO extends NamedTo {
    boolean enabled;

    @JsonCreator
    public AdminRestaurantTO(Integer id, String name, boolean enabled) {
        super(id, name);
        this.enabled = enabled;
    }
}
