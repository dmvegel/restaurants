package com.github.dmvegel.restaurants.common.service;

import com.github.dmvegel.restaurants.common.error.NotFoundException;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.function.Supplier;

public abstract class BaseService<E, R extends JpaRepository<E, Integer>> {
    protected final R repository;

    public BaseService(R repository) {
        this.repository = repository;
    }

    protected E getExisted(int id) {
        return repository.findById(id).orElseThrow(() -> new NotFoundException("Entity with id=" + id + " not found"));
    }

    protected E getOrNotFound(Supplier<Optional<E>> supplier, String message) {
        return supplier.get()
                .orElseThrow(() -> new NotFoundException(message));
    }
}
