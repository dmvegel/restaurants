package ru.javaops.restaurants.common.service;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.javaops.restaurants.common.error.NotFoundException;

public abstract class BaseService<E, R extends JpaRepository<E, Integer>> {
    protected final R repository;

    public BaseService(R repository) {
        this.repository = repository;
    }

    protected E getExisted(int id) {
        return repository.findById(id).orElseThrow(() -> new NotFoundException("Entity with id=" + id + " not found"));
    }
}
