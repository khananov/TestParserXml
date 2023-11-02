package ru.khananov.dataparser.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import ru.khananov.dataparser.DataSynchronizer;
import ru.khananov.entity.DepData;
import ru.khananov.jdbc.TransactionManager;
import ru.khananov.services.DepDataService;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

public class DepDataSynchronizerXmlImpl implements DataSynchronizer {
    private static final Logger logger = LoggerFactory.getLogger(DepDataSynchronizerXmlImpl.class);
    private final TransactionManager transactionManager;
    private final DepDataService depDataService;

    public DepDataSynchronizerXmlImpl(TransactionManager transactionManager, DepDataService depDataService) {
        this.transactionManager = transactionManager;
        this.depDataService = depDataService;
    }

    @Override
    public void synchronizeData(String inputFilePath) {
        try {
            transactionManager.beginTransaction();
            Set<DepData> xmlData = readDataFromXML(inputFilePath);
            Set<DepData> dbData = new HashSet<>(depDataService.getAll());
            synchronization(dbData, xmlData);
            transactionManager.commit();

            logger.info("Data synchronization completed successfully.");
        } catch (ParserConfigurationException | SAXException | IOException | SQLException e) {
            transactionManager.rollback();
            logger.info("Synchronization data from XML error: " + e.getMessage() + "\nRollback");
            logger.error(String.valueOf(e));
        }
    }

    private Set<DepData> readDataFromXML(String xmlFilePath) throws ParserConfigurationException, IOException, SAXException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document = builder.parse(new File(xmlFilePath));

        Element root = document.getDocumentElement();
        NodeList depDataElements = root.getElementsByTagName("Row");

        return getSetFromXmlNodeList(depDataElements);
    }

    private void synchronization(Set<DepData> dbData, Set<DepData> xmlData) {
        for (DepData xmlDepData : xmlData) {
            if (dbData.contains(xmlDepData)) {
                DepData dbDepData = findMatchingData(dbData, xmlDepData);
                if (dbDepData != null) {
                    dbDepData.setDescription(xmlDepData.getDescription());
                    depDataService.update(dbDepData);
                }
            } else {
                depDataService.save(xmlDepData);
            }
        }

        for (DepData dbDepData : dbData) {
            if (!xmlData.contains(dbDepData)) {
                depDataService.deleteById(dbDepData.getId());
            }
        }
    }
    private Set<DepData> getSetFromXmlNodeList(NodeList depDataElements) {
        Set<DepData> depDataSet = new HashSet<>();
        for (int i = 0; i < depDataElements.getLength(); i++) {
            Element depDataElement = (Element) depDataElements.item(i);
            String depCode = depDataElement.getElementsByTagName("DepCode").item(0).getTextContent();
            String depJob = depDataElement.getElementsByTagName("DepJob").item(0).getTextContent();
            String description = depDataElement.getElementsByTagName("Description").item(0).getTextContent();

            DepData depData = new DepData(depCode, depJob, description);
            depDataSet.add(depData);
        }

        return depDataSet;
    }

    private DepData findMatchingData(Set<DepData> dbData, DepData xmlDepData) {
        for (DepData dbDepData : dbData) {
            if (dbDepData.getDepCode().equals(xmlDepData.getDepCode())
                    && dbDepData.getDepJob().equals(xmlDepData.getDepJob())) {
                return dbDepData;
            }
        }

        return null;
    }
}