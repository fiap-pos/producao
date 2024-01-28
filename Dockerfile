FROM maven:3.9.6-amazoncorretto-17 AS build
RUN mkdir /app
WORKDIR /app
COPY . /app
RUN mvn clean package -DskipTests

FROM amazoncorretto:17.0.10
RUN mkdir /app
RUN apt-get update && apt-get install -y dumb-init
COPY --from=build /app/target/producao-*.jar /app/java-application.jar
WORKDIR /app
RUN addgroup --system producao-app && useradd -r producao-app -g producao-app
RUN chown -R producao-app:producao-app /app
USER producao-app
EXPOSE 8083
CMD "dumb-init" "java" "-jar" "java-application.jar"
