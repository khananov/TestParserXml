package ru.khananov;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

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
import java.sql.*;
import java.util.Properties;

public class Main {
    private static final Logger logger = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {
        Properties properties = loadProperties("configuration.properties");
        String dbUrl = properties.getProperty("db.url");
        String dbUsername = properties.getProperty("db.username");
        String dbPassword = properties.getProperty("db.password");
        String xmlFileName = properties.getProperty("result.parser.file.name");

        parseToXml("dbparser.xml", dbUrl, dbUsername, dbPassword);
    }

    private static void parseToXml(String xmlFileName, String dbUrl, String dbUsername, String dbPassword) {
        try (Connection connection = DriverManager.getConnection(dbUrl, dbUsername, dbPassword) ) {
            // Выполните SQL-запрос для извлечения данных из таблицы
            String sql = "SELECT depCode, depJob, description FROM parser";
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();

            // Создайте XML-документ
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document document = db.newDocument();

            // Создайте корневой элемент
            Element root = document.createElement("TableData");
            document.appendChild(root);

            while (resultSet.next()) {
                // Создайте элемент <row>
                Element row = document.createElement("row");
                root.appendChild(row);

                // Создайте элементы для полей
                Element depCode = document.createElement("depCode");
                depCode.appendChild(document.createTextNode(resultSet.getString("depCode")));
                row.appendChild(depCode);

                Element depJob = document.createElement("depJob");
                depJob.appendChild(document.createTextNode(resultSet.getString("depJob")));
                row.appendChild(depJob);

                Element description = document.createElement("description");
                description.appendChild(document.createTextNode(resultSet.getString("description")));
                row.appendChild(description);
            }

            // Сохраните XML в файл
            TransformerFactory tf = TransformerFactory.newInstance();
            Transformer transformer = tf.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");

            try (Writer out = new FileWriter(xmlFileName)) {
                transformer.transform(new DOMSource(document), new StreamResult(out));
            }

        } catch (SQLException | ParserConfigurationException | TransformerException | IOException e) {
            logger.error(e.getMessage());
        }
    }

    private static Properties loadProperties(String propertiesName) {
        Properties properties = new Properties();
        try {
            properties.load(ClassLoader.getSystemResourceAsStream(propertiesName));
        } catch (IOException e) {
            logger.error(e.getMessage());
        }

        return properties;
    }
}