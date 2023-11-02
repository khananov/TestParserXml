# Java Application for Data Export and Synchronization Using XML

This Java application allows you to perform two primary functions:

1. Export the contents of a database table to an XML file.
2. Synchronize the contents of a database table with a specified XML file.

***
## Technology stack
Java 17, Maven 3, JDBC, Postgresql, Logback, Docker

***

## For Local Deployment

Before running the application, you need to follow these steps:

1. Ensure that you have JDK 17 installed.

2. Unzip the project and package it using Maven. Assuming you're in the root directory and execute the following command:
```bash
$ mvn clean package
```

3. Create a database. Create and populate a table using the script init_db.sql, located in the ./scripts/sql folder.
4. Configure logging and database connection parameters in the application's configuration file - configuration.properties, located in the src/main/resources folder.

### Running the Application

The application should be executed from the command line (or use scripts in ./scripts folder), specifying the function to be performed and the XML file name:

1. **Exporting Data to an XML File:**
   
From *target* folder execute the following command:
```bash
java -jar TestParserXml-1.0.jar export data.xml
```

2. **Synchronizing Data with an XML File:**
   
From *target* folder execute the following command:
```bash
java -jar TestParserXml-1.0.jar sync data.xml
```
***

## For Deployment With Docker
Assuming you're in the root directory, just run:
```bash
$ mvn clean package
$ docker build -t parser .
```
