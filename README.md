# Java Application for Data Export and Synchronization Using XML

This Java application allows you to perform two primary functions:

1. Export the contents of a database table to an XML file.
2. Synchronize the contents of a database table with a specified XML file.

***
## Technology stack
Java 17, Maven 3, JDBC, Postgresql, Logback, Docker

***

## For Local Deployment

### Before Running the Application

Before running the application, you need to follow these steps:

1. Ensure that you have JDK 17 installed.
2. Unzip the project. 
3. Create a database using PostgreSQL. Create and fill a table using the script *init_db.sql*, located in the *./scripts/sql* folder.
4. Configure logging and database connection parameters in *configuration.properties*, located in the *src/main/resources* folder.
5. Assuming you're in the root directory and execute the following command (or use scripts in *./scripts* folder):
```bash
$ mvn clean package
```

### Running the Application

The application should be executed from the command line (or use scripts in *./scripts* folder), specifying the function to be performed and the XML file name. 
From *target* folder execute the following command:

1. **Exporting Data to an XML File:**
```bash
java -jar TestParserXml-1.0.jar export data.xml
```

2. **Synchronizing Data with an XML File:**
```bash
java -jar TestParserXml-1.0.jar sync data.xml
```
***

## For Deployment With Docker
Create a database using PostgreSQL in Docker and configure the connection to it in configuration.properties.
Assuming you're in the root directory, just run:

1. **Exporting Data to an XML File:**
```bash
$ mvn clean package
$ docker build -t parser .
$ docker run parser function=export filename=data.xml
```

2. **Synchronizing Data with an XML File:**
```bash
$ mvn clean package
$ docker build -t parser .
$ docker run parser function=sync filename=data.xml
```
