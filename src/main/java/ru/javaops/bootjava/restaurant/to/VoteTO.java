package ru.javaops.bootjava.restaurant.to;

import jakarta.validation.constraints.NotNull;

public record VoteTO(@NotNull Integer restaurantId) {
}
