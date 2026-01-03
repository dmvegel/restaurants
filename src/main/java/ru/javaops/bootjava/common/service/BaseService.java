package ru.javaops.bootjava.common.service;

import ru.javaops.bootjava.common.BaseRepository;
import ru.javaops.bootjava.common.error.NotFoundException;

public abstract class BaseService<E, R extends BaseRepository<E>> {
    protected final R repository;

    public BaseService(R repository) {
        this.repository = repository;
    }

    protected void deleteExisted(int id) {
        if (repository.delete(id) == 0) {
            throw new NotFoundException("Entity with id=" + id + " not found");
        }
    }

    protected E getExisted(int id) {
        return repository.findById(id).orElseThrow(() -> new NotFoundException("Entity with id=" + id + " not found"));
    }
}
