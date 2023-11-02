package ru.khananov.services.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.khananov.dao.CrudDao;
import ru.khananov.entity.DepData;
import ru.khananov.exceptions.DepDataNotFoundException;
import ru.khananov.services.DepDataService;

import java.util.List;

public class DepDataServiceImpl implements DepDataService {
    private static final Logger logger = LoggerFactory.getLogger(DepDataServiceImpl.class);
    private final CrudDao<DepData> depDataCrudDao;

    public DepDataServiceImpl(CrudDao<DepData> depDataCrudDao) {
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
        depDataCrudDao.save(depData);
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