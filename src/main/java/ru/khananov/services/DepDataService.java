package ru.khananov.services;

import ru.khananov.entity.DepData;

import java.util.List;

public interface DepDataService {
    DepData getById(Long id);

    List<DepData> getAll();

    void save(DepData depData);

    void update(DepData depData);

    void deleteById(Long id);
}