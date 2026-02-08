package com.github.dmvegel.restaurants.restaurant.to;

import jakarta.validation.constraints.NotNull;

public record VoteTO(@NotNull Integer restaurantId) {
}
