FROM eclipse-temurin:11-jdk-focal

WORKDIR /app

COPY .mvn/ .mvn
COPY mvnw pom.xml ./

COPY application/src ./application/src
COPY application/pom.xml ./application/pom.xml

COPY model/src ./model/src
COPY model/pom.xml ./model/pom.xml

COPY repository/src ./repository/src
COPY repository/pom.xml ./repository/pom.xml

COPY rest/src ./rest/src
COPY rest/pom.xml ./rest/pom.xml

COPY service/src ./service/src
COPY service/pom.xml ./service/pom.xml

RUN ./mvnw dependency:go-offline

CMD ["./mvnw", "spring-boot:run"]
