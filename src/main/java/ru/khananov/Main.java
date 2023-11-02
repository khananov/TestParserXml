package ru.khananov;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.khananov.configuration.DatabaseConnectionConfiguration;
import ru.khananov.dao.CrudDao;
import ru.khananov.dao.impl.DepDataDaoImpl;
import ru.khananov.dataparser.DataExporter;
import ru.khananov.dataparser.DataSynchronizer;
import ru.khananov.dataparser.impl.DepDataExporterXmlImpl;
import ru.khananov.dataparser.impl.DepDataSynchronizerXmlImpl;
import ru.khananov.entity.DepData;
import ru.khananov.services.DepDataService;
import ru.khananov.services.impl.DepDataServiceImpl;

import java.sql.*;

public class Main {
    private static final Logger logger = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {
        Connection connection = DatabaseConnectionConfiguration.getConnection("configuration.properties");
        CrudDao<DepData> depDataCrudDao = new DepDataDaoImpl(connection);
        DepDataService depDataService = new DepDataServiceImpl(depDataCrudDao);
        DataExporter dataExporterXml = new DepDataExporterXmlImpl(depDataService);
        DataSynchronizer dataSynchronizerXml = new DepDataSynchronizerXmlImpl(depDataService);

//        dataExporterXml.exportData("exportData.xml");
        dataSynchronizerXml.synchronizeData("exportData.xml");

        DatabaseConnectionConfiguration.closeConnection(connection);

//        String command = args[0];
//        String filename = args[1];
    }
}