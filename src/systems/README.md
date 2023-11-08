# Systems
Folder for all the systems. The [docker-compose.yml](./docker-compose.yaml)
contains the services for databases and message brokers. Each project in the
subsequent folders each have a README that expalin how to run the projects.

## Running the file
Use command `docker compose up -d` for detached mode.

## Compose files
**docker-compose.db.yaml:** Compose file for all of the databases.
**docker-compose.prod.yaml:** Compose file for running a production system

Running the in detached mode use the command: `docker compose -f <docker-file> up -d`. 
