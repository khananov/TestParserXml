FROM openjdk:17
RUN mkdir /app
COPY target/TestParserXml-1.0.jar app/parser.jar
ENTRYPOINT ["java", "-jar", "/app/parser.jar"]