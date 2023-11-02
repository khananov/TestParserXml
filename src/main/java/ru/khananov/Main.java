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
        if (args.length < 2) {
            logger.info("Usage: java -jar TestParserXml-1.0.jar <function> <xmlFileName.xml>");
            return;
        }

        String command = args[0];
        String xmlFileName = args[1];

        Connection connection = DatabaseConnectionConfiguration.getConnection("configuration.properties");
        CrudDao<DepData> depDataCrudDao = new DepDataDaoImpl(connection);
        DepDataService depDataService = new DepDataServiceImpl(depDataCrudDao);

        if (command.equals("export")) {
            DataExporter dataExporterXml = new DepDataExporterXmlImpl(depDataService);
            dataExporterXml.exportData(xmlFileName);
        } else if (command.equals("sync")) {
            DataSynchronizer dataSynchronizerXml = new DepDataSynchronizerXmlImpl(depDataService);
            dataSynchronizerXml.synchronizeData(xmlFileName);
        }

        DatabaseConnectionConfiguration.closeConnection(connection);

    }
}