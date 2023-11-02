package ru.khananov.dataparser.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import ru.khananov.dataparser.DataSynchronizer;
import ru.khananov.entity.DepData;
import ru.khananov.services.DepDataService;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class DepDataSynchronizerXmlImpl implements DataSynchronizer {
    private static final Logger logger = LoggerFactory.getLogger(DepDataSynchronizerXmlImpl.class);
    private final DepDataService depDataService;

    public DepDataSynchronizerXmlImpl(DepDataService depDataService) {
        this.depDataService = depDataService;
    }

    @Override
    public void synchronizeData(String inputFilePath) {
        try {
            List<DepData> xmlData = readDataFromXML(inputFilePath);
            List<DepData> dbData = depDataService.getAll();
            synchronization(dbData, xmlData);

            logger.info("Data synchronization completed successfully.");
        } catch (Exception e) {
            logger.error("Data synchronization error: " + e.getMessage(), e);
        }
    }

    private List<DepData> readDataFromXML(String xmlFilePath) {
        List<DepData> depDataList = new ArrayList<>();
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(new File(xmlFilePath));

            Element root = document.getDocumentElement();
            NodeList depDataElements = root.getElementsByTagName("Row");

            for (int i = 0; i < depDataElements.getLength(); i++) {
                Element depDataElement = (Element) depDataElements.item(i);
                String depCode = depDataElement.getElementsByTagName("DepCode").item(0).getTextContent();
                String depJob = depDataElement.getElementsByTagName("DepJob").item(0).getTextContent();
                String description = depDataElement.getElementsByTagName("Description").item(0).getTextContent();

                DepData depData = new DepData();
                depData.setDepCode(depCode);
                depData.setDepJob(depJob);
                depData.setDescription(description);
                depDataList.add(depData);
            }
        } catch (ParserConfigurationException | SAXException | IOException e) {
            logger.error("Reading data from XML error: " + e.getMessage(), e);
        }

        return depDataList;
    }

    private void synchronization(List<DepData> dbData, List<DepData> xmlData) {
        Set<DepData> xmlDataSet = new HashSet<>(xmlData);
        Set<DepData> dbDataSet = new HashSet<>(dbData);

        for (DepData xmlDepData : xmlData) {
            if (dbDataSet.contains(xmlDepData)) {
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
            if (!xmlDataSet.contains(dbDepData)) {
                depDataService.deleteById(dbDepData.getId());
            }
        }
    }

    private DepData findMatchingData(List<DepData> dbData, DepData xmlDepData) {
        for (DepData dbDepData : dbData) {
            if (dbDepData.getDepCode().equals(xmlDepData.getDepCode())
                    && dbDepData.getDepJob().equals(xmlDepData.getDepJob())) {
                return dbDepData;
            }
        }

        return null;
    }
}