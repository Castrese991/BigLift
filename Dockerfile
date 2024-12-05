# Fase di build: utilizza un'immagine con il JDK per compilare l'applicazione
FROM eclipse-temurin:17-jdk-focal AS build

# Imposta la directory di lavoro
WORKDIR /app

# Copia Maven Wrapper (mvnw) e la directory .mvn
COPY mvnw .
COPY .mvn .mvn/

# Copia i file del progetto (pom.xml e cartella src) nel container
COPY pom.xml .
COPY src ./src

# Rendi eseguibile il file mvnw
RUN chmod +x mvnw

# Esegui il comando Maven per costruire il file JAR
RUN ./mvnw clean package -DskipTests

# Fase di runtime: utilizza un'immagine più leggera con solo il JRE
FROM eclipse-temurin:17-jre-focal AS runtime

# Imposta la directory di lavoro
WORKDIR /app

# Copia il file JAR dal container di build
COPY --from=build /app/target/palestra-0.0.1-SNAPSHOT.jar /app/palestra.jar

# Espone la porta su cui l'applicazione è in ascolto
EXPOSE 7777
# CMD ["java", "-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=*:7778", "-jar", "palestra.jar"]

# Comando per eseguire l'applicazione
ENTRYPOINT ["java", "-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=*:5005", "-jar", "/app/palestra.jar"]