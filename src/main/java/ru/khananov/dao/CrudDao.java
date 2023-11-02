package ru.khananov.dao;

import java.util.List;
import java.util.Optional;

public interface CrudDao <T> {
    Optional<T> findById(Long id);

    List<T> findAll();

    void save(T entity);

    void update(Long id, T entity);

    void deleteById(Long id);
}