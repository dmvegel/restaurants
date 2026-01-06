package ru.javaops.bootjava.restaurant.to;

import jakarta.validation.constraints.NotNull;

public record VoteTo(@NotNull Integer restaurantId) {
}
