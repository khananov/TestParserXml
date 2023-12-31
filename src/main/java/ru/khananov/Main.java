package ru.khananov;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.khananov.jdbc.TransactionManager;
import ru.khananov.jdbc.impl.DatabaseConnection;
import ru.khananov.dao.DepDataCrudDao;
import ru.khananov.dao.impl.DepDataDaoImpl;
import ru.khananov.dataparser.DataExporter;
import ru.khananov.dataparser.DataSynchronizer;
import ru.khananov.dataparser.impl.DepDataExporterXmlImpl;
import ru.khananov.dataparser.impl.DepDataSynchronizerXmlImpl;
import ru.khananov.entity.DepData;
import ru.khananov.jdbc.impl.TransactionManagerImpl;
import ru.khananov.services.DepDataService;
import ru.khananov.services.impl.DepDataServiceImpl;

import java.sql.Connection;

public class Main {
    private static final Logger logger = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {
        if (args.length < 2) {
            logger.info("Usage: java -jar TestParserXml-1.0.jar <function> <xmlFile.xml>");
            return;
        }

        String command = args[0];
        String xmlFile = args[1];

        Connection connection = DatabaseConnection.getConnection("configuration.properties");
        TransactionManager transactionManager = new TransactionManagerImpl(connection);
        DepDataCrudDao<DepData> depDataCrudDao = new DepDataDaoImpl(connection, transactionManager);
        DepDataService depDataService = new DepDataServiceImpl(depDataCrudDao);

        if (command.equals("export")) {
            DataExporter dataExporterXml = new DepDataExporterXmlImpl(depDataService);
            dataExporterXml.exportData(xmlFile);
        } else if (command.equals("sync")) {
            DataSynchronizer dataSynchronizerXml = new DepDataSynchronizerXmlImpl(transactionManager, depDataService);
            dataSynchronizerXml.synchronizeData(xmlFile);
        }

        DatabaseConnection.closeConnection(connection);
    }
}