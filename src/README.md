# Source Code
Docker compose file in this folder bootstraps the whole project. The different configuration variables are located in the `.env` file.

TOOD:
- [x] Add sevices to the compose file
- [x] Create ENV file 
- [ ] Get the bootstrapping working
- [ ] Write some mock services


# Considerations
## Building the images
Currently the images need to be built somehow. There are two ways that I can think of:
- Use the context build option and then create some directories in the folder here.
- Build all the services first and then bootstrap the docker compose file

## Using external links
We could use [external links](https://stackoverflow.com/questions/41012766/docker-compose-external-links-not-able-to-connect) to try and split the project into multiple compose files. This would make it more managable and more like indepent services.

## Using multiple docker compose files in one command
We could use multiple docker compose files in one command to split the file alittle up.

Eg. we have file `docker-compose.supervisor.yaml` and `docker-compose.databases.yml`. We would then apply them using the command: `docker compose -f docker-compose.supervisor.yaml -f docker.compose.databases.yaml`-

