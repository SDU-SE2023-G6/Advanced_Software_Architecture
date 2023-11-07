# Spring with MongoDB and RabbitMQ
Spring with MongoDB integration and RabbitMQ.

## Development
Development is done using IntelliJ IDEA.

Useful commands:
* `mvn clean compile` Rebuild
* `mvn spring-boot:run` Run application

## Running the app using docker
The app requires that there is a MongoDB and a RabbitMQ instance running. Use the [docker-compose file in systems](../../docker-compose.yaml).

### Configurations
Look at the [docker-compose](./docker-compose.yml) and the [application confiuration](./src/main/resources/application.properties) file for the spring app.

### Building the app
Use the command `docker build . -t supervisor_backend:prod`.
