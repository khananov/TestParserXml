package ru.khananov.dao;

import ru.khananov.entity.DepData;

import java.util.Optional;

public interface DepDataCrudDao<T> extends CrudDao<T> {
    Optional<DepData> findByDepCodeAndDepJob(String depCode, String depJob);
}