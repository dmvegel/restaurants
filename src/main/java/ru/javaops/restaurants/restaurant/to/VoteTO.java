package ru.javaops.restaurants.restaurant.to;

import jakarta.validation.constraints.NotNull;

public record VoteTO(@NotNull Integer restaurantId) {
}
