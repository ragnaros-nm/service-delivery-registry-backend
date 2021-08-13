# Delivery Service Registry - Backend
Repositorio para le microservicio Springboot del proyecto DSR.


## Pre-Requisitos

 - Para que el microservicio pueda ser compilado y ejecutado se requiere tener una conexión a [Elasticsearch](https://www.elastic.co/guide/en/elasticsearch/reference/current/install-elasticsearch.html)
 en su ambiente local o en algun servidor.

 - Necesita tener instalado [Apache Maven](https://maven.apache.org/).
 - Necesita tener instalado [OpenJDK-11](https://jdk.java.net/archive/).

## Instalación y ejecución

- En la carpeta raiz del proyecto ejecute el siguien comando:
```sh
mvn compile package
```
- Una vez terminada la compilación ejecute el jar que se encuentra dentro de la carpeta **target**:
```sh
java -jar target/delivery.service.registry-0.6.4-SNAPSHOT
```





