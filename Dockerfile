FROM openjdk:17-jdk-slim

WORKDIR /app

# Copier les fichiers nécessaires
COPY pom.xml .
COPY src ./src

# Installer Maven et construire l'application
RUN apt-get update && apt-get install -y maven && apt-get clean
RUN mvn clean package -DskipTests

# Exposer le port 8080
EXPOSE 8080

# Lancer l'application
CMD ["java", "-jar", "target/regulation-prix-senegal-0.0.1-SNAPSHOT.jar"]
