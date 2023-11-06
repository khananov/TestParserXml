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

You need to follow these steps:

1. Ensure that you have JDK 17 and Maven 3 installed. 
2. Create a database using PostgreSQL. Create and fill a table using the script *init_db.sql*, located in the *./scripts/sql* folder.
3. Configure logging and database connection parameters in *configuration.properties*, located in the *src/main/resources* folder.
4. Assuming you're in the root directory and execute the following command (or use scripts in *./scripts* folder):
```bash
$ mvn clean package
```

### Running the Application

The application should be executed from the command line, specifying the function to be performed and the XML file name. 
From *target* folder execute the following command (or use scripts in *./scripts* folder):

1. **Exporting Data to an XML File:**
```bash
java -jar TestParserXml-1.0.jar export data.xml
```

2. **Synchronizing Data with an XML File:**
```bash
java -jar TestParserXml-1.0.jar sync data.xml
```

***

## For Deployment With Docker compose

### Before Running the Application

You need to follow these steps:

1. In the *configuration.properties* file, located in the *src/main/resources* folder, 
set the value of db.url to jdbc:postgresql://db:5432/parser
2. In the *docker-compose.yaml* file, located in the project's root folder,
specify the required command ('export' or 'sync') and the XML filename in the *command* parameter.
3. Assuming you're in the root directory and execute the following command (or use scripts in *./scripts* folder):
```bash
$ mvn clean package
```

### Running the Application

By default, when using the *export* command, the XML file will be copied to the 'output' folder at the project's root.
When using the 'sync' command, the XML file from the 'output' folder at the project's root is copied into the container,
and the application synchronizes the database.

Assuming you're in the root directory, just run:
```bash
$ docker compose up
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