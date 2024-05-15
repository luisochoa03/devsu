FROM openjdk:22

# Variables de entorno para la aplicación Java
ENV JAVA_OPTS=""

# Puerto en el que se ejecutará la aplicación
EXPOSE 7077

# Crear un directorio en el contenedor para la aplicación
WORKDIR /app

# Copiar el archivo JAR de la aplicación al contenedor
COPY target/cliente-app.jar app.jar

# Comando para ejecutar la aplicación
ENTRYPOINT exec java $JAVA_OPTS -jar /app/app.jar