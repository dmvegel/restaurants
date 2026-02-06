package com.github.dmvegel.restaurants.common.service;

import com.github.dmvegel.restaurants.common.error.NotFoundException;
import org.springframework.data.jpa.repository.JpaRepository;

public abstract class BaseService<E, R extends JpaRepository<E, Integer>> {
    protected final R repository;

    public BaseService(R repository) {
        this.repository = repository;
    }

    protected E getExisted(int id) {
        return repository.findById(id).orElseThrow(() -> new NotFoundException("Entity with id=" + id + " not found"));
    }
}
