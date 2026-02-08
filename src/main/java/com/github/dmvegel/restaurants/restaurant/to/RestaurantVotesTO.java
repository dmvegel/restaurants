package com.github.dmvegel.restaurants.restaurant.to;

import com.github.dmvegel.restaurants.common.to.NamedTo;
import lombok.EqualsAndHashCode;
import lombok.Value;

@Value
@EqualsAndHashCode(callSuper = true)
public class RestaurantVotesTO extends NamedTo {
    long voteCount;

    public RestaurantVotesTO(Integer id, String name, long voteCount) {
        super(id, name);
        this.voteCount = voteCount;
    }
}
