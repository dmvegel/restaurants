package ru.javaops.bootjava.restaurant.to;

import lombok.EqualsAndHashCode;
import lombok.Value;
import ru.javaops.bootjava.common.to.NamedTo;

@Value
@EqualsAndHashCode(callSuper = true)
public class RestaurantVotesTO extends NamedTo {
    public RestaurantVotesTO(Integer id, String name, long voteCount) {
        super(id, name);
        this.voteCount = voteCount;
    }
    long voteCount;
}
