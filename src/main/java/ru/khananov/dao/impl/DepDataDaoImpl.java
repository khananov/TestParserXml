package ru.khananov.dao.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.khananov.dao.CrudDao;
import ru.khananov.entity.DepData;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class DepDataDaoImpl implements CrudDao<DepData> {
    private static final Logger logger = LoggerFactory.getLogger(DepDataDaoImpl.class);
    private final Connection connection;

    public DepDataDaoImpl(Connection connection) {
        this.connection = connection;
    }

    private static class DepDataRowMapper {
        static List<DepData> mapToDepData(ResultSet resultSet) {
            if (resultSet == null) {
                logger.info("ResultSet is null");
                return new ArrayList<>();
            }

            List<DepData> depDataList = new ArrayList<>();
            try {
                while (resultSet.next()) {
                    depDataList.add(new DepData(
                            resultSet.getLong("id"),
                            resultSet.getString("dep_code"),
                            resultSet.getString("dep_job"),
                            resultSet.getString("description")
                    ));
                }
            } catch (SQLException e) {
                logger.error("Mapping to depData error: " + e.getMessage(), e);
            }

            return depDataList;
        }
    }

    @Override
    public Optional<DepData> findById(Long id) {
        String sql = "SELECT * FROM dep_data WHERE id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            return DepDataRowMapper.mapToDepData(resultSet).stream().findFirst();
        } catch (SQLException e) {
            logger.error("FindById error: " + e.getMessage(), e);
            return Optional.empty();
        }
    }

    @Override
    public List<DepData> findAll() {
        String sql = "SELECT * FROM dep_data";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            return DepDataRowMapper.mapToDepData(resultSet);
        } catch (SQLException e) {
            logger.error("FindAll error: " + e.getMessage(), e);
            return new ArrayList<>();
        }
    }

    @Override
    public void save(DepData entity) {
        String sql = "INSERT INTO dep_data (dep_code, dep_job, description) VALUES (?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, entity.getDepCode());
            preparedStatement.setString(2, entity.getDepJob());
            preparedStatement.setString(3, entity.getDescription());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            logger.error("Save error: " + e.getMessage(), e);
        }
    }

    @Override
    public void update(Long id, DepData entity) {
        String sql = "UPDATE dep_data SET description = ? WHERE id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, entity.getDescription());
            preparedStatement.setLong(2, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            logger.error("Update error: " + e.getMessage(), e);
        }
    }

    @Override
    public void deleteById(Long id) {
        String sql = "DELETE FROM dep_data WHERE id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            logger.error("Delete error: " + e.getMessage(), e);
        }
    }
}