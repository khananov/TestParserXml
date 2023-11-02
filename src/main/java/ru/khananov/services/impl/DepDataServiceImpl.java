package ru.khananov.services.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.khananov.dao.DepDataCrudDao;
import ru.khananov.entity.DepData;
import ru.khananov.exceptions.DepDataAlreadyExistsException;
import ru.khananov.exceptions.DepDataNotFoundException;
import ru.khananov.services.DepDataService;

import java.util.List;
import java.util.Optional;

public class DepDataServiceImpl implements DepDataService {
    private static final Logger logger = LoggerFactory.getLogger(DepDataServiceImpl.class);
    private final DepDataCrudDao<DepData> depDataCrudDao;

    public DepDataServiceImpl(DepDataCrudDao<DepData> depDataCrudDao) {
        this.depDataCrudDao = depDataCrudDao;
    }

    @Override
    public DepData getById(Long id) {
        return depDataCrudDao.findById(id)
                .orElseGet(() -> {
                    logger.debug(new DepDataNotFoundException(id).getMessage());
                    throw new DepDataNotFoundException(id);
                });
    }

    @Override
    public List<DepData> getAll() {
        List<DepData> depDataList = depDataCrudDao.findAll();
        if (depDataList.isEmpty()) {
            logger.debug("Table dep_data is empty.");
        }

        return depDataList;
    }

    @Override
    public void save(DepData depData) {
        String depCode = depData.getDepCode();
        String depJob = depData.getDepJob();
        Optional<DepData> dbDepData =
                depDataCrudDao.findByDepCodeAndDepJob(depCode, depJob);
        if (dbDepData.isEmpty())
            depDataCrudDao.save(depData);
        else {
            logger.error(new DepDataAlreadyExistsException(depCode, depJob).getMessage());
            throw new DepDataAlreadyExistsException(depCode, depJob);
        }

    }

    @Override
    public void update(DepData depData) {
        depDataCrudDao.update(depData.getId(), depData);
    }

    @Override
    public void deleteById(Long id) {
        depDataCrudDao.deleteById(id);
    }
}