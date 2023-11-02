package ru.khananov.dataparser.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import ru.khananov.dataparser.DataExporter;
import ru.khananov.entity.DepData;
import ru.khananov.services.DepDataService;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.List;

public class DepDataExporterXmlImpl implements DataExporter {
    private static final Logger logger = LoggerFactory.getLogger(DepDataExporterXmlImpl.class);
    private final DepDataService depDataService;

    public DepDataExporterXmlImpl(DepDataService depDataService) {
        this.depDataService = depDataService;
    }

    @Override
    public void exportData(String outputFilePath) {
        try (Writer writer = new FileWriter(outputFilePath)) {
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
            Document document = docBuilder.newDocument();

            Element rootElement = document.createElement("DepData");
            document.appendChild(rootElement);

            List<DepData> depDataList = depDataService.getAll();
            parseData(document, rootElement, depDataList);

            TransformerFactory tf = TransformerFactory.newInstance();
            Transformer transformer = tf.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.transform(new DOMSource(document), new StreamResult(writer));

            logger.info("Export data to XML file (path: " + outputFilePath + ") completed successfully");
        } catch (ParserConfigurationException | TransformerException | IOException e) {
            logger.info("Export to XML file error: " + e.getMessage());
            logger.error(String.valueOf(e));
        }
    }

    private void parseData(Document document, Element rootElement, List<DepData> depDataList) {
        depDataList.forEach(depData -> {
            Element row = document.createElement("Row");
            rootElement.appendChild(row);

            Element depCode = document.createElement("DepCode");
            depCode.appendChild(document.createTextNode(depData.getDepCode()));
            row.appendChild(depCode);

            Element depJob = document.createElement("DepJob");
            depJob.appendChild(document.createTextNode(depData.getDepJob()));
            row.appendChild(depJob);

            Element description = document.createElement("Description");
            description.appendChild(document.createTextNode(depData.getDescription()));
            row.appendChild(description);
        });
    }
}