# Dockerfile Instructions

## 1. Descargar imagen de Oracle

   Primero, crea un usuario y contraseña en [Oracle Container Registry](https://container-registry.oracle.com/). Luego, ejecuta los siguientes comandos:

       - docker login container-registry.oracle.com --username email --password pass
       -  docker pull container-registry.oracle.com/database/free:latest

   Asegúrate de estar en el directorio `dockerfile`.



## 2. Levantar Oracle en Docker

   Ejecuta el siguiente comando:

       -  docker-compose -f docker-compose-oracle.yml up -d



## 3. Crear tablas

   Ejecuta los siguientes comandos:

       - docker cp banco.sql oracle_db_container:/tmp/banco.sql
       - docker exec -it oracle_db_container bash -c 'sqlplus system/mysecretpassword@//localhost:1521/FREE @/tmp/banco.sql'



## 4. Opcional - Validar la instancia

   Ejecuta los siguientes comandos:

       - docker cp instance.sql oracle_db_container:/tmp/instance.sql
       - docker exec -it oracle_db_container bash -c 'sqlplus system/mysecretpassword@//localhost:1521/FREE @/tmp/instance.sql'



## 5. Levantar Kafka y Zookeeper

   Ejecuta el siguiente comando:

       - docker-compose -f docker-compose-kafka.yml up -d