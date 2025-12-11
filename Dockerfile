FROM maven:3.9.8-eclipse-temurin-17 AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
ENV MAVEN_OPTS="-Dhttps.protocols=TLSv1.2 -Djava.security.egd=file:/dev/./urandom"
RUN mvn -q -Dmaven.test.skip=true clean package --no-transfer-progress

FROM eclipse-temurin:17-jre
WORKDIR /app
COPY --from=build /app/target/backend.jar /app/app.jar
ENV JAVA_OPTS=""
EXPOSE 8083
ENTRYPOINT ["sh","-c","java $JAVA_OPTS -jar /app/app.jar"]
