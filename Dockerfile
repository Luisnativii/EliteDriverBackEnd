# Imagen base de Java 21 con Alpine (liviana y segura)
FROM eclipse-temurin:21-jdk-alpine

# Crear carpeta dentro del contenedor
WORKDIR /app

# Copiar el archivo .jar desde tu máquina al contenedor
COPY target/*.jar app.jar

# Exponer el puerto 8080 (que usa Spring Boot)
EXPOSE 8080

# Comando que se ejecutará al iniciar el contenedor
ENTRYPOINT ["java", "-jar", "app.jar"]
