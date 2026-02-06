package com.github.dmvegel.restaurants.restaurant.to;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.github.dmvegel.restaurants.common.to.NamedTo;
import lombok.EqualsAndHashCode;
import lombok.Value;

@Value
@EqualsAndHashCode(callSuper = true)
public class RestaurantTO extends NamedTo {
    @JsonCreator
    public RestaurantTO(Integer id, String name) {
        super(id, name);
    }
}
