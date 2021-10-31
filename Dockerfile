FROM adoptopenjdk/openjdk11:alpine-jre
ARG JAR_FILE=target/stock.jar
WORKDIR /opt/app
COPY ${JAR_FILE} stock.jar
ENTRYPOINT ["java","-jar","stock.jar"]